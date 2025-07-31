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

    public Piece(String pieceId, StateInterface initState) {
        this.pieceId = pieceId;
        this.state = initState;
    }

    @Override
    public StateInterface onCommand(CommandInterface cmd, Map<Moves.Pair, List<Piece>> cell2piece, long now) {
        System.out.println("[PIECE] " + pieceId + " received command: " + cmd.getType());

        if (cmd.getType().equals(CommandType.MOVE) && cmd.getParams().isEmpty()) {
            Moves.Pair end = state.getPhysics().getEndCell();
            setPosition(new int[] { end.r, end.c });
            CommandInterface idleCmd = new Command((int) now, pieceId, CommandType.IDLE, List.of(end));
            return this.onCommand(idleCmd, cell2piece, now);
        }

        String key = cmd.getType().toString().toLowerCase();
        StateInterface next = state.getTransitions().get(key);
        if (next == null) {
            System.out.println("[PIECE] No transition defined for: " + key);
            return state;
        }

        next.setCommand(cmd);
        this.state = next;

        System.out.println("[PIECE] " + pieceId + " transitioned to state: " + next.getType().name().toLowerCase());
        return next;
    }

    @Override
    public void reset(int startMs) {
        if (this.state.getCommand() != null)
            this.state.reset(this.state.getCommand());
    }

    public void update(int now) {
        System.out.println("[PIECE] Updating piece: " + this.pieceId);
        state.update(now);
    }

    public void drawOnBoard(ImgInterface targetImg, BoardInterface board, int nowMs) {
        int[] cell = this.state.getPhysics().getPos();
        int wPix = board.getCellWPix();
        int hPix = board.getCellHPix();
        ImgInterface sprite = this.state.getCurrentSprite(nowMs);

        double[] posM = this.state.getPhysics().getCurrPosM();
        int xPix = (int) posM[0];
        int yPix = (int) posM[1];

        System.out.println("Drawing piece " + pieceId + " at cell [" + cell[0] + ", " + cell[1] + "] -> pixel [" + xPix
                + ", " + yPix + "]");

        if (pieceId.contains("W")) {
            System.out.println("  *** WHITE PIECE: " + pieceId + " at [" + xPix + ", " + yPix + "] ***");
        }

        if (sprite instanceof Img) {
            Img spriteImg = (Img) sprite;
            BufferedImage originalSprite = spriteImg.getBufferedImage();
            Image scaledSprite = originalSprite.getScaledInstance(wPix, hPix, Image.SCALE_SMOOTH);
            BufferedImage resizedSprite = new BufferedImage(wPix, hPix, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resizedSprite.createGraphics();
            g2.drawImage(scaledSprite, 0, 0, null);
            g2.dispose();

            Graphics2D g = targetImg.getBufferedImage().createGraphics();
            g.setComposite(AlphaComposite.SrcOver);
            g.drawImage(resizedSprite, xPix, yPix, null);
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
}
