package it1engine.impl;

import it1engine.enums.CommandType;
import it1engine.impl.factory.PieceFactory;
import it1engine.impl.model.Board;
import it1engine.impl.model.CursorPositionManager;
import it1engine.impl.model.Img;
import it1engine.impl.model.KeyboardInputHandler;
import it1engine.impl.model.Moves;
import it1engine.impl.model.command.Command;
import it1engine.impl.model.command.CommandDispatcher;
import it1engine.impl.model.command.CommandProducer;
import it1engine.impl.model.command.CommandQueue;
import it1engine.impl.model.command.listeners.*;
import it1engine.interfaces.*;
import it1engine.interfaces.command.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class Main {

    private static void runGameLoopManually(Board board,
            Map<String, PieceInterface> pieces,
            CommandQueueInterface commandQueue,
            CommandDispatcher dispatcher,
            ImgInterface baseImage,
            CursorPositionManager cursorManager) {

        long startNs = System.nanoTime();

        for (PieceInterface p : pieces.values())
            p.reset(0);

        while (true) {
            int nowMs = (int) ((System.nanoTime() - startNs) / 1_000_000);

            // עיבוד פקודות
            CommandInterface cmd;
            while ((cmd = commandQueue.poll()) != null) {
                dispatcher.dispatch(cmd, baseImage, nowMs);
            }

            // עדכון כל הכלים
            for (PieceInterface p : pieces.values())
                p.update(nowMs);

            // ציור מחודש של הקנבס
            ImgInterface imgCanvas = baseImage.clone();

            // ציור כל הכלים לפי מצב נוכחי
            for (PieceInterface p : pieces.values()) {
                p.drawOnBoard(imgCanvas, board, nowMs);
            }

            // ציור קורסורים
            for (String playerId : List.of("P1", "P2")) {
                int[] pos = cursorManager.getCursor(playerId);
                Color color = playerId.equals("P1") ? new Color(0, 0, 255, 100) : new Color(0, 255, 0, 100);
                int x = pos[1] * board.getCellWPix();
                int y = pos[0] * board.getCellHPix();
                Graphics2D g = ((Img) imgCanvas).getBufferedImage().createGraphics();
                g.setColor(color);
                g.fillRect(x, y, board.getCellWPix(), board.getCellHPix());
                g.dispose();
            }

            board.setImg(imgCanvas);
            board.repaint();

            try {
                Thread.sleep(1000 / 30);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public static void main(String[] args) throws Exception {

        ImgInterface img = new Img().read("board.png");
        Board board = new Board(100, 100, 0, 0, 8, 8, img);

        PieceFactory factory = new PieceFactory(board);
        URL resourceUrl = Main.class.getClassLoader().getResource("pieces");
        if (resourceUrl == null)
            throw new IllegalStateException("Resource folder 'pieces' not found");
        Path baseFolder = Paths.get(resourceUrl.toURI());

        factory.generateLibrary(baseFolder);

        Map<String, PieceInterface> pieces = new HashMap<>();
        for (Map.Entry<String, int[]> entry : getBoardPieceLocations().entrySet()) {
            String pieceId = entry.getKey();
            int[] pos = entry.getValue();
            String type = pieceId.split("_")[0];

            Moves.Pair cell = new Moves.Pair(pos[0], pos[1]);
            PieceInterface piece = factory.createPiece(pieceId, cell);

            CommandInterface cmd = new Command(0, pieceId, CommandType.IDLE, List.of(cell));
            piece.onCommand(cmd, new HashMap<>(), 0);

            pieces.put(pieceId, piece);
        }

        System.out.println("Setting up command handlers...");
        CommandProducerInterface commandProducer = new CommandProducer();
        CommandQueueInterface commandQueue = new CommandQueue();
        CursorPositionManager cursorManager = new CursorPositionManager(8, 8);
        KeyboardInputHandler inputHandler = new KeyboardInputHandler(commandProducer, commandQueue, cursorManager,
                board, pieces);
        CommandDispatcher dispatcher = new CommandDispatcher();

        dispatcher.register(new IdleCommandListener(pieces, board, cursorManager));
        dispatcher.register(new MoveCommandListener(pieces, board, cursorManager));
        dispatcher.register(new JumpCommandListener(pieces, board, cursorManager));
        dispatcher.register(new RestCommandListener(pieces, board, cursorManager));

        System.out.println("Setting up base image...");
        board.showWithInput(inputHandler);
        BufferedImage baseBoard = ((Img) board.getImg()).getBufferedImage();
        BufferedImage base = new BufferedImage(baseBoard.getWidth(), baseBoard.getHeight(), baseBoard.getType());
        Graphics2D gBase = base.createGraphics();
        gBase.drawImage(baseBoard, 0, 0, null);
        gBase.dispose();

        System.out.println("מתחיל לולאת משחק...");
        runGameLoopManually(board, pieces, commandQueue, dispatcher, new Img(base), cursorManager);

    }

    private static Map<String, int[]> getBoardPieceLocations() {
        System.out.println("מגדיר מיקומים ראשוניים של הכלים...");
        Map<String, int[]> locations = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            locations.put("PB_" + (i + 1), new int[] { 1, i });
            locations.put("PW_" + (i + 1), new int[] { 6, i });
        }
        locations.put("RB_1", new int[] { 0, 0 });
        locations.put("NB_1", new int[] { 0, 1 });
        locations.put("BB_1", new int[] { 0, 2 });
        locations.put("QB", new int[] { 0, 3 });
        locations.put("KB", new int[] { 0, 4 });
        locations.put("BB_2", new int[] { 0, 5 });
        locations.put("NB_2", new int[] { 0, 6 });
        locations.put("RB_2", new int[] { 0, 7 });

        locations.put("RW_1", new int[] { 7, 0 });
        locations.put("NW_1", new int[] { 7, 1 });
        locations.put("BW_1", new int[] { 7, 2 });
        locations.put("QW", new int[] { 7, 3 });
        locations.put("KW", new int[] { 7, 4 });
        locations.put("BW_2", new int[] { 7, 5 });
        locations.put("NW_2", new int[] { 7, 6 });
        locations.put("RW_2", new int[] { 7, 7 });
        return locations;
    }
}
