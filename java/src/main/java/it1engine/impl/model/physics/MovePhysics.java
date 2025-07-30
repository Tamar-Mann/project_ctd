package it1engine.impl.model.physics;

import it1engine.enums.CommandType;
import it1engine.impl.model.Moves;
import it1engine.impl.model.command.Command;
import it1engine.interfaces.BoardInterface;
import it1engine.interfaces.command.CommandInterface;

import java.util.List;

public class MovePhysics extends AbstractPhysics {

    private double[] movementVec;
    private double movementVecLength;
    private double durationSec;

    public MovePhysics(BoardInterface board, double speedCellsPerSec) {
        super(board, speedCellsPerSec);
    }

    @Override
    public void reset(CommandInterface cmd) {
        if (cmd.getParams().size() < 2)
            throw new IllegalArgumentException("MOVE command must have 2 params: startCell and endCell");

        this.startCell = cmd.getParams().get(0);
        this.endCell = cmd.getParams().get(1);
        this.currPosM = board.cellToM(startCell);
        this.startMs = cmd.getTimestamp();

        double[] start = board.cellToM(startCell);
        double[] end = board.cellToM(endCell);
        this.movementVec = new double[] { end[0] - start[0], end[1] - start[1] };
        this.movementVecLength = Math.sqrt(movementVec[0] * movementVec[0] + movementVec[1] * movementVec[1]);
        this.durationSec = movementVecLength / param;
    }

    @Override
    public CommandInterface update(CommandInterface cmd, long nowMs) {
        double secondsPassed = (nowMs - startMs) / 1000.0;

        // חישוב וקטור תנועה
        double ratio = secondsPassed / durationSec;
        if (ratio >= 1.0) {
            currPosM = board.cellToM(endCell);
            return null;
        }

        double[] startM = board.cellToM(startCell);
        double[] endM = board.cellToM(endCell);
        currPosM = new double[] {
                startM[0] + (endM[0] - startM[0]) * ratio,
                startM[1] + (endM[1] - startM[1]) * ratio
        };

        return null;
    }

}
