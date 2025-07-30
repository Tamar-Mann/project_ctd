package it1engine.interfaces;

import it1engine.impl.model.Moves;
import it1engine.interfaces.command.CommandInterface;

/**
 * Interface for physics handling
 */
public interface PhysicsInterface {

    /**
     * Reset physics state with a new command.
     */
    void reset(CommandInterface cmd);

    /**
     * Update physics state based on current time (in ms).
     */
    CommandInterface update(CommandInterface cmd, long nowMs);

    boolean isMovementBlocker();

    /**
     * Whether this piece can be captured.
     */
    boolean canBeCaptured();

    /**
     * Whether this piece can capture other pieces.
     */
    boolean canCapture();

    /**
     * Returns the current position (in board cell coordinates).
     */
    int[] getPos();

    Moves.Pair getEndCell();
    void setPos(double[] currPosM);
}
