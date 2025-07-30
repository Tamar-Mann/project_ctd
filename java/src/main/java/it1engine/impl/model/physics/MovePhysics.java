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
        List<Moves.Pair> params = cmd.getParams();
        if (params.isEmpty() || !(params.get(0) instanceof Moves.Pair)) {
            throw new IllegalArgumentException("[MovePhysics] Missing or invalid start cell");
        }

        this.startCell = (Moves.Pair) params.get(0);
        this.currPosM = board.cellToM(startCell);
        this.startMs = cmd.getTimestamp();

        if (params.size() >= 2 && params.get(1) instanceof Moves.Pair) {
            this.endCell = (Moves.Pair) params.get(1);
            double[] startPos = board.cellToM(startCell);
            double[] endPos = board.cellToM(endCell);
            movementVec = new double[] { endPos[0] - startPos[0], endPos[1] - startPos[1] };
            movementVecLength = Math.hypot(movementVec[0], movementVec[1]);

            if (movementVecLength == 0)
                movementVecLength = 1;

            movementVec[0] /= movementVecLength;
            movementVec[1] /= movementVecLength;

            durationSec = movementVecLength / param; // param = speed (m/s)
        } else {
            // אין תנועה – כלומר מצב עמידה
            this.endCell = startCell;
            movementVec = new double[] { 0, 0 };
            movementVecLength = 0;
            durationSec = 0;
        }

        System.out.println("[MovePhysics] startCell=" + startCell + " currPosM=" + java.util.Arrays.toString(currPosM));
    }

    @Override
    public CommandInterface update(CommandInterface cmd, long nowMs) {
        double secondsPassed = (nowMs - startMs) / 1000.0;

        currPosM = board.cellToM(startCell);
        currPosM[0] += movementVec[0] * secondsPassed * param;
        currPosM[1] += movementVec[1] * secondsPassed * param;

        if (secondsPassed >= durationSec) {
            return new Command((int) nowMs, cmd.getPieceId(), CommandType.MOVE, List.of());
        }
        return null;
    }
}
