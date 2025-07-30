package it1engine.impl.model.command.listeners;

import it1engine.impl.model.Img;
import it1engine.impl.model.CursorPositionManager;
import it1engine.interfaces.BoardInterface;
import it1engine.interfaces.PieceInterface;
import it1engine.interfaces.ImgInterface;
import it1engine.interfaces.command.CommandInterface;
import it1engine.interfaces.command.CommandListenerInterface;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Map;

public class MoveCommandListener implements CommandListenerInterface {

    private final Map<String, PieceInterface> pieces;
    private final BoardInterface board;
    private final CursorPositionManager cursorManager;

    public MoveCommandListener(Map<String, PieceInterface> pieces, BoardInterface board, CursorPositionManager cursorManager) {
        this.pieces = pieces;
        this.board = board;
        this.cursorManager = cursorManager;
    }

    @Override
    public boolean supports(CommandInterface cmd) {
        return cmd.getType().equalsIgnoreCase("MOVE");
    }

    @Override
    public void handle(CommandInterface cmd, ImgInterface canvas, long now) {
        PieceInterface piece = pieces.get(cmd.getPieceId());
        if (piece != null) {
            piece.onCommand(cmd, null, now);
            
            System.out.println("Handling MOVE for " + cmd.getPieceId());

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


// 6:52
// אם עוברים בדרך מעל כלי של היריב - אוכלים אותו
// 6:52
// אם הוא מספיק לברוח - הוא הורויח
// 6:52
// אם הוא עושה קפיצה ונוחת עלי - אכל אותי
// 6:53
// איך בודקים מ יאוכל את מי?
// 6:53
// מי שהתחיל בפעולה אחרון
// 6:53
// (מלבד מי שבמנוחה או idle שהוא וודאי נאכל)
// 6:53
// זה גם נכון לשאלה אם 2 כלים זזים ונפגשים מי יאכל את מי
// 6:53
// מי שהתחיל בפעולה אחרון
// 6:54
