
// === JumpPhysics.java ===
package it1engine.impl.model.physics;
import it1engine.interfaces.BoardInterface;
import it1engine.interfaces.command.CommandInterface;

public class JumpPhysics extends AbstractPhysics {

    public JumpPhysics(BoardInterface board, double durationSec) {
        super(board, 0.0);
        this.durationSec = durationSec;
    }

    @Override
    public void reset(CommandInterface cmd) {
        this.startCell = cmd.getParams().get(0);
        this.currPosM = board.cellToM(startCell);
        this.startMs = cmd.getTimestamp();
        this.startPos = board.cellToM(startCell);
        this.endPos = startPos;
    }

    @Override
    public void update(int nowMs) {
        super.update(nowMs);
    }

    @Override
    public boolean canBeCaptured() {
        return false;
    }
}



// package it1engine.impl.model.physics;

// import it1engine.impl.model.Moves;
// import it1engine.interfaces.BoardInterface;
// import it1engine.enums.CommandType;
// import it1engine.impl.model.command.Command;
// import it1engine.interfaces.command.CommandInterface;

// import java.util.List;

// /**
//  * JumpPhysics – עמידה במקום לזמן קצוב (לא ניתן להיאכל).
//  */
// public class JumpPhysics extends AbstractPhysics {

//     private double durationSec;
//     private CommandInterface originalCmd;

//     public JumpPhysics(BoardInterface board, double durationSec) {
//         super(board, 0.0); // אין תנועה בפועל
//         this.durationSec = durationSec;
//     }

//     @Override
//     public void reset(CommandInterface cmd) {
//         this.startCell = (Moves.Pair) cmd.getParams().get(0);
//         this.currPosM = board.cellToM(startCell);
//         this.startMs = cmd.getTimestamp();
//         this.originalCmd = cmd;
//     }

//     @Override
//     public void update(int nowMs) {
//         double elapsed = (nowMs - startMs) / 1000.0;
//         if (elapsed >= durationSec) {
//             System.out.println("[JUMP_PHYSICS] Jump done – switching to IDLE");

//             if (stateMachine != null) {
//                 stateMachine.setState("idle");
//             }
//             if (currState != null) {
//                 currState.getGraphics().reset();
//             }
//         }
//     }

//     @Override
//     public boolean canBeCaptured() {
//         return false;
//     }
// }

