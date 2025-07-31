// === MovePhysics.java ===
package it1engine.impl.model.physics;

import it1engine.impl.model.Moves;
import it1engine.interfaces.BoardInterface;
import it1engine.interfaces.command.CommandInterface;

public class MovePhysics extends AbstractPhysics {

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

        this.startPos = board.cellToM(startCell);
        this.endPos = board.cellToM(endCell);
        this.movementVec = new double[] { endPos[0] - startPos[0], endPos[1] - startPos[1] };
        double length = Math.sqrt(movementVec[0] * movementVec[0] + movementVec[1] * movementVec[1]);
        this.durationSec = length / param;
    }

    @Override
    public void update(int nowMs) {
        double elapsedSec = (nowMs - startMs) / 1000.0;

        if (elapsedSec >= durationSec) {
            currPosM = endPos.clone(); // תנועה הסתיימה
            finished = true;
        } else {
            double ratio = elapsedSec / durationSec;
            currPosM[0] = startPos[0] + ratio * movementVec[0];
            currPosM[1] = startPos[1] + ratio * movementVec[1];
        }

        System.out.printf("[PHYSICS] %s: currPosM = (%.2f, %.2f), progress = %.2f\n",
                this.getClass().getSimpleName(), currPosM[0], currPosM[1], elapsedSec / durationSec);

        super.update(nowMs);
    }

}
