package it1engine.impl.model;

import it1engine.enums.CommandType;
import it1engine.impl.model.command.Command;
import it1engine.interfaces.BoardInterface;
import it1engine.interfaces.PieceInterface;
import it1engine.interfaces.command.CommandInterface;
import it1engine.interfaces.command.CommandProducerInterface;
import it1engine.interfaces.command.CommandQueueInterface;

import java.util.List;
import java.util.Map;

public class KeyboardInputHandler {

    private final CommandProducerInterface producer;
    private final CommandQueueInterface queue;
    private final CursorPositionManager cursorManager;
    private final BoardInterface board;
    private final Map<String, PieceInterface> pieces;

    // שדות לשמירת הכלי הנבחר
    private String selectedPieceId = null;
    private Moves.Pair selectedPiecePosition = null;

    public KeyboardInputHandler(CommandProducerInterface producer,
            CommandQueueInterface queue,
            CursorPositionManager cursorManager,
            BoardInterface board,
            Map<String, PieceInterface> pieces) {
        this.producer = producer;
        this.queue = queue;
        this.cursorManager = cursorManager;
        this.board = board;
        this.pieces = pieces;
    }

    public void inputHandler(String key, int timestamp) {
        String playerId = getPlayerIdFromKey(key);
        if (playerId == null)
            return;

        // תזוזת קרסור - תמיד מתבצעת
        switch (key) {
            case "W" -> cursorManager.move("P1", -1, 0);
            case "S" -> cursorManager.move("P1", 1, 0);
            case "A" -> cursorManager.move("P1", 0, -1);
            case "D" -> cursorManager.move("P1", 0, 1);
            case "UP" -> cursorManager.move("P2", -1, 0);
            case "DOWN" -> cursorManager.move("P2", 1, 0);
            case "LEFT" -> cursorManager.move("P2", 0, -1);
            case "RIGHT" -> cursorManager.move("P2", 0, 1);
        }

        // טיפול במקשי פעולה
        if (key.equals("ENTER") || key.equals("SPACE")) {
            handleMoveAction(playerId, timestamp);
        } else if (key.equals("SHIFT") || key.equals("BACKSPACE")) {
            handleJumpAction(playerId, timestamp);
        } else if (key.equals("ESCAPE")) {
            // ביטול בחירה
            selectedPieceId = null;
            selectedPiecePosition = null;
            System.out.println("Selection cancelled");
        }
    }

    private void handleMoveAction(String playerId, int timestamp) {
        int[] pos = cursorManager.getCursor(playerId);
        Moves.Pair currentPos = new Moves.Pair(pos[0], pos[1]);

        if (selectedPieceId == null) {
            // שלב 1: בחירת כלי
            String pieceId = findPieceAt(currentPos);
            if (pieceId != null) {
                selectedPieceId = pieceId;
                selectedPiecePosition = currentPos;
                System.out.println("Selected piece: " + pieceId + " at " + currentPos);
            } else {
                System.out.println("No piece found at " + currentPos);
            }
        } else {
            // שלב 2: תנועה למיקום חדש
            if (!currentPos.equals(selectedPiecePosition)) {
                System.out
                        .println("Moving " + selectedPieceId + " from " + selectedPiecePosition + " to " + currentPos);

                CommandInterface cmd = producer.createMoveCommand(selectedPieceId, selectedPiecePosition, currentPos,
                        timestamp);
                if (cmd != null) {
                    queue.add(cmd);
                    System.out.println("Move command added to queue");
                }
            } else {
                System.out.println("Same position - no movement needed");
            }

            // איפוס הבחירה
            selectedPieceId = null;
            selectedPiecePosition = null;
        }
    }

    private void handleJumpAction(String playerId, int timestamp) {
        int[] pos = cursorManager.getCursor(playerId);
        Moves.Pair currentPos = new Moves.Pair(pos[0], pos[1]);

        if (selectedPieceId == null) {
            // בחירת כלי לקפיצה
            String pieceId = findPieceAt(currentPos);
            if (pieceId != null) {
                selectedPieceId = pieceId;
                selectedPiecePosition = currentPos;
                System.out.println("Selected piece for jump: " + pieceId + " at " + currentPos);
            }
        } else {
            // ביצוע קפיצה
            if (!currentPos.equals(selectedPiecePosition)) {
                System.out
                        .println("Jumping " + selectedPieceId + " from " + selectedPiecePosition + " to " + currentPos);

                CommandInterface cmd = producer.createJumpCommand(selectedPieceId, currentPos, timestamp);
                if (cmd != null) {
                    queue.add(cmd);
                }
            }

            selectedPieceId = null;
            selectedPiecePosition = null;
        }
    }

    private String findPieceAt(Moves.Pair position) {
        return pieces.values().stream()
                .filter(p -> {
                    int[] piecePos = p.getState().getPhysics().getPos();
                    return piecePos[0] == position.r && piecePos[1] == position.c;
                })
                .map(PieceInterface::getPieceId)
                .findFirst()
                .orElse(null);
    }

    private String getPlayerIdFromKey(String key) {
        return switch (key) {
            case "W", "A", "S", "D", "SPACE", "ENTER", "SHIFT", "BACKSPACE", "ESCAPE" -> "P1";
            case "UP", "LEFT", "DOWN", "RIGHT" -> "P2";
            default -> null;
        };
    }
}