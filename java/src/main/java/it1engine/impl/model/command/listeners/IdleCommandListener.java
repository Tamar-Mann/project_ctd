package it1engine.impl.model.command.listeners;

import it1engine.impl.model.CursorPositionManager;
import it1engine.interfaces.BoardInterface;
import it1engine.interfaces.ImgInterface;
import it1engine.interfaces.PieceInterface;
import it1engine.interfaces.command.CommandInterface;
import it1engine.interfaces.command.CommandListenerInterface;
import java.util.Map;

public class IdleCommandListener implements CommandListenerInterface {
    
    private final Map<String, PieceInterface> pieces;
    private final BoardInterface board;
    private final CursorPositionManager cursorManager;
    // private ISubject subject = new Subject();

    public IdleCommandListener(Map<String, PieceInterface> pieces, BoardInterface board,
            CursorPositionManager cursorManager) {
        this.pieces = pieces;
        this.board = board;
        this.cursorManager = cursorManager;
    }

    @Override
    public boolean supports(CommandInterface cmd) {
        return cmd.getType().equalsIgnoreCase("IDLE");
    }

    @Override
    public void handle(CommandInterface cmd, ImgInterface canvas, long now) {
        PieceInterface piece = pieces.get(cmd.getPieceId());
        if (piece != null) {
            piece.onCommand(cmd, null, now);    

            //piece.drawOnBoard(canvas, board, (int) now);

            // int[] cursorPos = cursorManager.getCursor(cmd.getPieceId());

            // int x = cursorPos[1] * board.getCellWPix();
            // int y = cursorPos[0] * board.getCellHPix();
            // Graphics2D g = ((Img) canvas).getBufferedImage().createGraphics();
            // g.setColor(cmd.getPieceId().equals("P1") ? new Color(0, 0, 255, 100) : new
            // Color(0, 255, 0, 100));
            // g.fillRect(x, y, board.getCellWPix(), board.getCellHPix());
            // g.dispose();
        }
    }

}
