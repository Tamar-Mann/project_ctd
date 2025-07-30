package it1engine.impl.model.physics;

import it1engine.impl.model.Moves;
import it1engine.interfaces.BoardInterface;
import it1engine.interfaces.PhysicsInterface;
import it1engine.interfaces.command.CommandInterface;

/**
 * AbstractPhysics – base class for all physics types.
 */
public abstract class AbstractPhysics implements PhysicsInterface {

    protected final BoardInterface board;

    protected Moves.Pair startCell;
    protected Moves.Pair endCell;
    protected double[] currPosM; // (x, y) in meters
    protected long startMs;

    protected double param; // speed or duration (depends on subtype)

    public AbstractPhysics(BoardInterface board, double param) {
        this.board = board;
        this.param = param;
    }

    // --- abstract methods ---
    @Override
    public abstract void reset(CommandInterface cmd);

    @Override
    public abstract CommandInterface update(CommandInterface cmd, long nowMs);

    // --- default behaviors ---
    @Override
    public int[] getPos() {
        if (currPosM == null) {
            System.err.println("[PHYSICS] currPosM is null – falling back to [0,0]");
            return new int[] { 0, 0 };
        }

        // המר את currPosM חזרה לתאים
        Moves.Pair cell = board.mToCell(currPosM);
        return new int[] {
                cell.r, // row
                cell.c // col
        };
    }

    @Override
    public boolean canBeCaptured() {
        return true;
    }

    @Override
    public boolean canCapture() {
        return true;
    }

    @Override
    public boolean isMovementBlocker() {
        return false;
    }

    // --- getters/setters if needed ---
    public Moves.Pair getStartCell() {
        return startCell;
    }

    public Moves.Pair getEndCell() {
        return endCell;
    }

    public double[] getCurrPosM() {
        return currPosM;
    }

    public long getStartMs() {
        return startMs;
    }

    public double getParam() {
        return param;
    }

    public void setParam(double param) {
        this.param = param;
    }

    @Override
    public void setPos(double[] currPosM) {
        this.currPosM = currPosM;
    }
    public void setStartCell(Moves.Pair startCell) {
        this.startCell = startCell;
    }

    public void setCurrPosM(double[] currPosM) {
        this.currPosM = currPosM;
    }

}
