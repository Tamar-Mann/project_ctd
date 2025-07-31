package it1engine.impl.model.physics;

import java.util.Arrays;

import it1engine.impl.model.Moves;
import it1engine.interfaces.BoardInterface;
import it1engine.interfaces.PhysicsInterface;
import it1engine.interfaces.StateInterface;
import it1engine.interfaces.StateMachineInterface;
import it1engine.interfaces.command.CommandInterface;

public abstract class AbstractPhysics implements PhysicsInterface {

    protected final BoardInterface board;
    protected boolean finished = false;

    protected Moves.Pair startCell;
    protected Moves.Pair endCell;
    protected double[] currPosM; // (x, y) in meters
    protected long startMs;

    protected double param; // speed or duration (depends on subtype)

    // תוספות נחוצות לתזוזה בפועל
    protected Moves.Pair cell; // מיקום התא הנוכחי
    protected double[] startPos;
    protected double[] endPos;
    protected double[] movementVec;
    protected double durationSec;

    protected StateInterface currState;
    protected StateMachineInterface stateMachine;

    public AbstractPhysics(BoardInterface board, double param) {
        this.board = board;
        this.param = param;
    }

    @Override
    public void reset(CommandInterface cmd) {
        finished = false;
        System.out.println(
                "[DEBUG] RESET Physics called for state: " + this.getClass().getSimpleName() + " pos=" + startCell);
    }

    @Override
    public void update(int nowMs) {

        if (nowMs < startMs) {
            System.out.println("[PHYSICS] nowMs < startMs, skipping frame");
            return;
        }

        if (startCell == null || endCell == null || currPosM == null)
            return;

        double elapsedSec = (nowMs - startMs) / 1000.0;
        double progress = elapsedSec / durationSec;
        if (progress > 1.0)
            progress = 1.0;

        currPosM = new double[] {
                startPos[0] + movementVec[0] * progress,
                startPos[1] + movementVec[1] * progress
        };

        if (progress >= 1.0 && !finished) {
            currPosM = endPos;
            cell = endCell;
            finished = true;
            System.out.println("[PHYSICS] Movement complete for: " + this.getClass().getSimpleName());
        }
    }

    // isfinished...
    public boolean isFinished() {
        return currPosM != null && Arrays.equals(currPosM, endPos);
    }

    @Override
    public double[] getCurrPosM() {
        return currPosM;
    }

    @Override
    public int[] getPos() {
        if (currPosM == null) {
            System.err.println("[PHYSICS] currPosM is null – falling back to [0,0]");
            return new int[] { 0, 0 };
        }

        Moves.Pair cell = board.mToCell(currPosM);
        return new int[] { cell.r, cell.c };
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

    // --- Setters/Getters ---
    public Moves.Pair getStartCell() {
        return startCell;
    }

    public Moves.Pair getEndCell() {
        return endCell;
    }

    // public double[] getCurrPosM() {
    // return currPosM;
    // }

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

    public void setStartPos(double[] startPos) {
        this.startPos = startPos;
    }

    public void setEndPos(double[] endPos) {
        this.endPos = endPos;
    }

    public void setMovementVec(double[] movementVec) {
        this.movementVec = movementVec;
    }

    public void setDurationSec(double durationSec) {
        this.durationSec = durationSec;
    }

    public void setCurrState(StateInterface currState) {
        this.currState = currState;
    }

    public void setStateMachine(StateMachineInterface stateMachine) {
        this.stateMachine = stateMachine;
    }
}
