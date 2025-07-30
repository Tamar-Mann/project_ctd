package it1engine.impl.model.physics;

import it1engine.impl.model.Moves;
import it1engine.interfaces.BoardInterface;
import it1engine.interfaces.command.CommandInterface;
import it1engine.impl.model.command.Command;
import it1engine.enums.CommandType;

import java.util.List;

/**
 * JumpPhysics – עמידה במקום לזמן קצוב (לא ניתן להיאכל).
 */
public class JumpPhysics extends AbstractPhysics {

    private double durationSec;

    public JumpPhysics(BoardInterface board, double durationSec) {
        super(board, 0.0); // אין תנועה, רק משך
        this.durationSec = durationSec;
    }

    @Override
    public void reset(CommandInterface cmd) {
        this.startCell = (Moves.Pair) cmd.getParams().get(0);
        this.currPosM = board.cellToM(startCell);
        this.startMs = cmd.getTimestamp();
    }

    @Override
    public CommandInterface update(CommandInterface cmd, long nowMs) {
        double elapsed = (nowMs - startMs) / 1000.0;
        if (elapsed >= durationSec) {
            return new Command((int)nowMs, cmd.getPieceId(), CommandType.JUMP, List.of());
        }
        return null;
    }

    @Override
    public boolean canBeCaptured() {
        return false; // אי אפשר לאכול במהלך קפיצה
    }
}
