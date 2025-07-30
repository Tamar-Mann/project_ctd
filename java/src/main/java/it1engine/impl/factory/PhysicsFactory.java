package it1engine.impl.factory;

import it1engine.impl.model.Moves;
import it1engine.impl.model.physics.*;
import it1engine.interfaces.BoardInterface;
import it1engine.interfaces.PhysicsInterface;
import org.json.JSONObject;

/**
 * PhysicsFactory – creates appropriate Physics object based on state/config.
 */
public class PhysicsFactory {
    private final BoardInterface board;

    public PhysicsFactory(BoardInterface board) {
        this.board = board;
    }

    public PhysicsInterface create(Moves.Pair startCell, String stateName, JSONObject cfg) {
        double speed = cfg.optDouble("speed_m_per_sec", 0.0);
        double duration = cfg.optDouble("duration_ms", 1000) / 1000.0;

        PhysicsInterface phys;
        String name = stateName.toLowerCase();

        if (name.equals("move") || name.endsWith("_move")) {
            phys = new MovePhysics(board, speed);
        } else if (name.equals("jump")) {
            phys = new JumpPhysics(board, duration);
        } else if (name.endsWith("rest") || name.equals("rest")) {
            phys = new RestPhysics(board, duration);
        } else {
            phys = new IdlePhysics(board);
        }

        // אתחול מיקום ראשוני
        if (phys instanceof AbstractPhysics p) {
            p.setStartCell(startCell);
            p.setCurrPosM(board.cellToM(startCell));
        }

        return phys;
    }
}
