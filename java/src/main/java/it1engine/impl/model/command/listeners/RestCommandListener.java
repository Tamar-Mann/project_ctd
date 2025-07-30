package it1engine.impl.model.command.listeners;

import it1engine.impl.model.Img;
import it1engine.impl.model.CursorPositionManager;
import it1engine.interfaces.BoardInterface;
import it1engine.interfaces.PieceInterface;
import it1engine.interfaces.ImgInterface;
import it1engine.interfaces.command.CommandInterface;
import it1engine.interfaces.command.CommandListenerInterface;

import java.util.Map;

public class RestCommandListener implements CommandListenerInterface {

    private final Map<String, PieceInterface> pieces;
    private final BoardInterface board;
    private final CursorPositionManager cursorManager;

    public RestCommandListener(Map<String, PieceInterface> pieces, BoardInterface board, CursorPositionManager cursorManager) {
        this.pieces = pieces;
        this.board = board;
        this.cursorManager = cursorManager;
    }

    @Override
    public boolean supports(CommandInterface cmd) {
        String type = cmd.getType().toUpperCase();
        return type.equals("LONG_REST") || type.equals("SHORT_REST");
    }

    @Override
    public void handle(CommandInterface cmd, ImgInterface canvas, long now) {
        PieceInterface piece = pieces.get(cmd.getPieceId());
        if (piece != null) {
            piece.onCommand(cmd, null, now);
            // piece.drawOnBoard(canvas, board, (int) now);

            int[] pos = cursorManager.getCursor(cmd.getPieceId());
            int x = pos[1] * board.getCellWPix();
            int y = pos[0] * board.getCellHPix();

            // Graphics2D g = ((Img) canvas).getBufferedImage().createGraphics();
            // Color color = cmd.getPieceId().equals("P1") ? new Color(0, 0, 255, 100) : new Color(0, 255, 0, 100);
            // g.setColor(color);
            // g.fillRect(x, y, board.getCellWPix(), board.getCellHPix());
            // g.dispose();
        }
    }
}
