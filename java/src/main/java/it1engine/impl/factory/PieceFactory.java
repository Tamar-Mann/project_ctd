package it1engine.impl.factory;

import it1engine.interfaces.PieceFactoryInterface;
import it1engine.interfaces.PieceInterface;
import it1engine.enums.CommandType;
import it1engine.enums.StateType;
import it1engine.impl.model.Board;
import it1engine.impl.model.Moves;
import it1engine.impl.model.Piece;
import it1engine.impl.model.State;
import it1engine.impl.model.command.Command;
import it1engine.interfaces.BoardInterface;
import it1engine.interfaces.GraphicsInterface;
import it1engine.interfaces.MovesInterface;
import it1engine.interfaces.PhysicsInterface;
import it1engine.interfaces.StateInterface;

import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * PieceFactory class
 */
public class PieceFactory implements PieceFactoryInterface {
    private final BoardInterface board;
    private final GraphicsFactory gfxFactory;
    private final PhysicsFactory physFactory;
    private final Map<String, StateInterface> templates = new HashMap<>();
    private final Map<StateInterface, JSONObject> configByState = new HashMap<>();

    /**
     * Initialize piece factory with board and
     * generates the library of piece templates from the pieces directory.
     */
    public PieceFactory(Board board) {
        this.board = board;
        this.gfxFactory = new GraphicsFactory();
        this.physFactory = new PhysicsFactory(board);
    }

    public void generateLibrary(Path piecesRoot) throws Exception {

        System.out.println("מחפש בתוך: " + piecesRoot.toAbsolutePath());

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(piecesRoot)) {
            for (Path sub : stream) {
                if (Files.isDirectory(sub)) {
                    System.out.println((sub.getFileName().toString()));
                    templates.put(sub.getFileName().toString(), buildStateMachine(sub));
                }
            }

        }
    }

    /**
     * Build a state machine for a piece from its directory.
     * 
     * @throws IOException
     */
    private StateInterface buildStateMachine(Path pieceDir) throws IOException {
        int W = board.getWCells(), H = board.getHCells();
        Dimension cellPx = new Dimension(board.getCellWPix(), board.getCellHPix());
        Map<StateType, StateInterface> states = new HashMap<>();
        Path statesDir = pieceDir.resolve("states");

        if (!Files.exists(statesDir))
            throw new IllegalStateException("Missing states dir: " + statesDir);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(statesDir)) {
            for (Path stateDir : stream) {
                if (!Files.isDirectory(stateDir))
                    continue;
                StateType name = StateType.valueOf(stateDir.getFileName().toString().toUpperCase());
                JSONObject cfg = readJson(stateDir.resolve("config.json"));
                Path movesPath = stateDir.resolve("moves.txt");
                MovesInterface moves = Files.exists(movesPath) ? new Moves(movesPath, H, W) : null;

                JSONObject gfxCfg = cfg.optJSONObject("graphics") != null ? cfg.optJSONObject("graphics")
                        : new JSONObject();
                JSONObject physCfg = cfg.optJSONObject("physics") != null ? cfg.optJSONObject("physics")
                        : new JSONObject();

                Path spritesPath = stateDir.resolve("sprites");
                GraphicsInterface gfx = gfxFactory.load(spritesPath, gfxCfg.toMap(),
                        new int[] { cellPx.width, cellPx.height });
                Moves.Pair dummyStart = new Moves.Pair(0, 0); // אם אין לך מיקום התחלתי אמיתי
                PhysicsInterface phys = physFactory.create(dummyStart, name.toString(), physCfg);

                StateInterface st = new State(moves, gfx, phys);

                configByState.put(st, cfg);
                st.setType(name);
                states.put(name, st);
            }
        }

        Path transCsv = pieceDir.resolve("states").resolve("transitions.csv");
        if (Files.exists(transCsv)) {
            List<String> lines = Files.readAllLines(transCsv);
            for (String line : lines) {
                String l = line.strip();
                if (l.isEmpty() || l.startsWith("#") || l.toLowerCase().startsWith("from_state"))
                    continue;
                String[] parts = l.split(",");
                if (parts.length < 3)
                    continue;

                String from = parts[0].trim();
                String event = parts[1].trim().toLowerCase();
                String to = parts[2].trim();
                StateInterface src = states.get(StateType.valueOf(from.toUpperCase()));
                StateInterface dst = states.get(StateType.valueOf(to.toUpperCase()));

                if (src != null && dst != null) {
                    src.setTransition(event, dst);
                }
            }
        }

        // fallback
        StateInterface idle = states.get(StateType.IDLE);
        if (idle != null && idle.getTransitions().isEmpty()) {
            if (states.containsKey("move"))
                idle.setTransition("move", states.get("move"));
            if (states.containsKey("jump"))
                idle.setTransition("jump", states.get("jump"));
        }

        return idle;
    }

    private static JSONObject readJson(Path p) {
        try {
            if (Files.exists(p))
                return new JSONObject(Files.readString(p));
        } catch (Exception ignored) {
        }
        return new JSONObject();
    }

    /**
     * Create a piece of the specified type at the given cell.
     */
    @Override
    public PieceInterface createPiece(String code, Moves.Pair cell) {
        // חילוץ סוג הכלי מתוך הקוד (אם צריך)
        String type = code.split("_")[0];
        StateInterface tmpl = templates.get(type);
        if (tmpl == null)
            throw new IllegalArgumentException("Unknown piece type " + type);

        // יצירת עותק חדש של סטייטים
        StateInterface idleClone = cloneStateMachine(tmpl, cell);

        // יצירת הכלי
        Piece piece = new Piece(code, idleClone);

        // הכנת פקודת התחלה
        Command cmd = new Command(0, piece.getPieceId(), CommandType.IDLE, List.of(cell));
        idleClone.reset(cmd); // ← קודם נותנים לפקודת מצב
        for (StateInterface st : templates.values()) {
            st.getPhysics().reset(cmd);
        }

        piece.reset(0); // ← אחר כך מריצים reset של הכלי

        return piece;
    }

    private StateInterface cloneStateMachine(StateInterface templateIdle, Moves.Pair cell) {
        Map<StateInterface, StateInterface> map = new HashMap<>();
        Deque<StateInterface> stack = new ArrayDeque<>();
        stack.push(templateIdle);

        while (!stack.isEmpty()) {
            StateInterface orig = stack.pop();
            if (map.containsKey(orig))
                continue;

            // יצירת physics חדש עם המיקום הנכון
            PhysicsInterface newPhys = physFactory.create(cell, orig.getType().name(),
                    configByState.getOrDefault(orig, new JSONObject()));

            Command dummyCmd = new Command(0, "dummy", CommandType.IDLE, List.of(cell, cell));
            newPhys.reset(dummyCmd);

            // ...existing code...
            // יצירת עותק של הסטייט עם המיקום החדש
            StateInterface copy = new State(
                    orig.getMoves(),
                    gfxFactory.load(
                            orig.getGraphics().getSpritesFolder(),
                            configByState.getOrDefault(orig, new JSONObject()).optJSONObject("graphics") != null
                                    ? configByState.getOrDefault(orig, new JSONObject()).optJSONObject("graphics")
                                            .toMap()
                                    : new HashMap<>(),
                            new int[] { board.getCellWPix(), board.getCellHPix() }),
                    newPhys);
            // ...existing code...

            // ...existing code...
            copy.setType(orig.getType());

            // הוסף מעבר idle רק אם זה State של IDLE
            if (orig.getType() == StateType.IDLE) {
                copy.setTransition("idle", copy); // מעבר לעצמו עבור IDLE
            }

            map.put(orig, copy);
            // ...existing code...

            // הוספת כל הטרנזיציות לסטאק
            for (StateInterface nextState : orig.getTransitions().values()) {
                if (!map.containsKey(nextState)) {
                    stack.push(nextState);
                }
            }
        }

        // עדכון הטרנזיציות
        for (Map.Entry<StateInterface, StateInterface> entry : map.entrySet()) {
            StateInterface orig = entry.getKey();
            StateInterface copy = entry.getValue();

            for (Map.Entry<String, StateInterface> trans : orig.getTransitions().entrySet()) {
                copy.setTransition(trans.getKey(), map.get(trans.getValue()));
            }
        }

        return map.get(templateIdle);
    }
}
