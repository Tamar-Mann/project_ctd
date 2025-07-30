package it1engine.impl.model;

import it1engine.interfaces.PieceInterface;
import it1engine.enums.CommandType;
import it1engine.impl.model.command.Command;
import it1engine.interfaces.BoardInterface;
import it1engine.interfaces.ImgInterface;
import it1engine.interfaces.StateInterface;
import it1engine.interfaces.command.CommandInterface;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

/**
 * Piece class
 */
public class Piece implements PieceInterface {
    private String pieceId;
    private StateInterface state;
    private Long lastActionTime; // זמן הפעולה האחרון

    /**
     * Initialize a piece with ID and initial state.
     */
    public Piece(String pieceId, StateInterface initState) {
        this.pieceId = pieceId;
        this.state = initState;
    }

    /**
     * Handle a command for this piece.
     */
    @Override
    public StateInterface onCommand(CommandInterface cmd, Map<Moves.Pair, List<Piece>> cell2piece, long now) {
        System.out.println("[PIECE] " + pieceId + " received command: " + cmd.getType());

        // שלב 1: טיפול ב-MOVE ריק (תנועה שהסתיימה)
        if (cmd.getType().equals(CommandType.MOVE) && cmd.getParams().isEmpty()) {
            // עדכון מיקום לתא סופי
            Moves.Pair end = state.getPhysics().getEndCell();
            setPosition(new int[] { end.r, end.c });

            // שלח פקודת IDLE אוטומטית
            CommandInterface idleCmd = new Command((int)now, pieceId, CommandType.IDLE, List.of(end));
            return this.onCommand(idleCmd, cell2piece, now);
        }

        // שלב 2: מעבר למצב הבא לפי מכונת המצבים
        String key = cmd.getType().toLowerCase();
        StateInterface next = state.getTransitions().get(key);
        if (next == null) {
            System.out.println("[PIECE] No transition defined for: " + key);
            return state;
        }

        // שלב 3: ביצוע מעבר: reset ו־setCommand
        next.reset(cmd);
        next.setCommand(cmd); // ← ודא שמחלקת State תומכת בזה
        this.state = next;

        System.out.println("[PIECE] " + pieceId + " transitioned to state: " + key);
        return next;
    }

    // if (isCommandPossible(cmd)) {
    // this.state = this.state.processCommand(cmd, nowMs);
    // }
    // }

    /**
     * Reset the piece to idle state.
     */
    @Override
    public void reset(int startMs) {
        if (this.state.getCommand() != null)
            this.state.reset(this.state.getCommand());
    }

    /**
     * Update the piece state based on current time.
     */
    public void update(int now) {
        System.out.println("[PIECE] Updating piece: " + this.pieceId + " at position: [" + this.state.getPhysics().getPos()[0] + ", " + this.state.getPhysics().getPos()[1] + "]");
        CommandInterface endCmd = state.getPhysics().update(this.getState().getCommand(), now);
        if (endCmd != null) {
            onCommand(endCmd, null, now); // ← מפעיל MOVE ריק
        }

        state.getGraphics().update(now);
    }

    public void drawOnBoard(ImgInterface targetImg, BoardInterface board, int nowMs) {
        int[] cell = this.state.getPhysics().getPos(); // [row, col]
        int wPix = board.getCellWPix();
        int hPix = board.getCellHPix();
        ImgInterface sprite = this.state.getCurrentSprite(nowMs);

        // השתמש בגודל התא המדויק (100% מגודל התא)
        int pieceWidth = wPix;
        int pieceHeight = hPix;

        // מיקום מדויק בתוך התא (בלי padding נוסף כי הBoard כבר מוסיף padding)
        int x = cell[1] * wPix;
        int y = cell[0] * hPix;

        System.out.println("Drawing piece " + pieceId + " at cell [" + cell[0] + ", " + cell[1] + "] -> pixel [" + x
                + ", " + y + "] (no additional padding)");

        // בדיקה מיוחדת לכלים לבנים
        if (pieceId.contains("W")) {
            System.out.println("  *** WHITE PIECE: " + pieceId + " at [" + x + ", " + y + "] ***");
        }

        // צור עותק של הספרייט בגודל הנכון
        if (sprite instanceof Img) {
            Img spriteImg = (Img) sprite;
            BufferedImage originalSprite = spriteImg.getBufferedImage();

            // שנה גודל לגודל התא המדויק
            Image scaledSprite = originalSprite.getScaledInstance(pieceWidth, pieceHeight, Image.SCALE_SMOOTH);
            BufferedImage resizedSprite = new BufferedImage(pieceWidth, pieceHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resizedSprite.createGraphics();
            g2.drawImage(scaledSprite, 0, 0, null);
            g2.dispose();

            // צייר את הספרייט המוקטן
            Graphics2D g = targetImg.getBufferedImage().createGraphics();
            g.setComposite(AlphaComposite.SrcOver);
            g.drawImage(resizedSprite, x, y, null);
            g.dispose();
        }
    }

    @Override
    public String getPieceId() {
        return pieceId;
    }

    @Override
    public int[] getPosition() {
        return state.getPhysics().getPos();
    }

    @Override
    public void setPosition(int[] position) {
        this.state.getPhysics().setPos(new double[] { position[0], position[1] });
    }

    @Override
    public void setPieceId(String pieceId) {
        this.pieceId = pieceId;
    }

    @Override
    public StateInterface getState() {
        return state;
    }

    public Long getLastActionTime() {
        return lastActionTime;
    }

    public void setLastActionTime(long time) {
        this.lastActionTime = time;
    }

    /**
     * Check if command is possible for this piece.
     */
    private boolean isCommandPossible(CommandInterface cmd) {
        // TODO: Implement
        // implement!!
        return state.canTransition(0);
    }

}
