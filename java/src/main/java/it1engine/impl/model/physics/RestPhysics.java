package it1engine.impl.model.physics;

import it1engine.impl.model.Moves;
import it1engine.interfaces.BoardInterface;
import it1engine.interfaces.command.CommandInterface;
import it1engine.impl.model.command.Command;
import it1engine.enums.CommandType;

import java.util.List;

/**
 * RestPhysics – עמידה במקום לזמן קצוב (לא יכול לאכול, חוסם תנועה).
 */
public class RestPhysics extends AbstractPhysics {

    private double durationSec;

    public RestPhysics(BoardInterface board, double durationSec) {
        super(board, 0.0); // אין תנועה, רק זמן מנוחה
        this.durationSec = durationSec;
    }

    @Override
    public void reset(CommandInterface cmd) {
        this.startCell = (Moves.Pair) cmd.getParams().get(0);
        this.currPosM = board.cellToM(startCell);
        this.startMs = cmd.getTimestamp();
        this.startPos = currPosM;
        this.endPos = currPosM;
    }

    @Override
    public void update(int nowMs) {
        double elapsed = (nowMs - startMs) / 1000.0;
        if (elapsed >= durationSec) {
            System.out.println("[PHYSICS] Rest complete for: " + this.getClass().getSimpleName());
        }
    }

    @Override
    public boolean canCapture() {
        return false; // אי אפשר לאכול בזמן מנוחה
    }

    @Override
    public boolean isMovementBlocker() {
        return true; // כן חוסם תנועה
    }
}
