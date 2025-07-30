package it1engine.impl.model.physics;

import it1engine.impl.model.Moves;
import it1engine.interfaces.BoardInterface;
import it1engine.interfaces.command.CommandInterface;

/**
 * IdlePhysics - no animation, piece stays במקום קבוע.
 */
public class IdlePhysics extends AbstractPhysics {

    public IdlePhysics(BoardInterface board) {
        super(board, 0.0); // אין צורך בפרמטר תנועה
    }

    @Override
    public void reset(CommandInterface cmd) {

        System.out.println("IdlePhysics reset for: " + startCell);
        if (!cmd.getParams().isEmpty() && cmd.getParams().get(0) instanceof Moves.Pair) {
            this.startCell = (Moves.Pair) cmd.getParams().get(0);
        } else {
            throw new IllegalArgumentException("[IdlePhysics] Missing or invalid position param");
        }

        this.currPosM = board.cellToM(startCell);
        this.startMs = cmd.getTimestamp();

        System.out.println("[IdlePhysics] startCell=" + startCell + " currPosM=" + java.util.Arrays.toString(currPosM));
    }

    // @Override
    // public CommandInterface update(CommandInterface cmd, long nowMs) {
    //     // Idle – אין שינוי, אין פקודה חדשה
    //     return null;
    // }

    @Override
    public boolean canCapture() {
        return true;
    }

    @Override
    public boolean isMovementBlocker() {
        return true;
    }
}
