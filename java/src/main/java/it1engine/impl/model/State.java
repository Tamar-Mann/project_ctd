package it1engine.impl.model;

import it1engine.enums.StateType;
import it1engine.interfaces.GraphicsInterface;
import it1engine.interfaces.ImgInterface;
import it1engine.interfaces.MovesInterface;
import it1engine.interfaces.PhysicsInterface;
import it1engine.interfaces.StateInterface;
import it1engine.interfaces.command.CommandInterface;

import java.util.HashMap;
import java.util.Map;

public class State implements StateInterface {
    private GraphicsInterface graphics;
    private PhysicsInterface physics;
    private Map<String, StateInterface> transitions;
    private MovesInterface moves;
    private StateType type;
    private CommandInterface command;

    /**
     * Initialize state with moves, graphics, and physics components.
     */
    public State(MovesInterface moves, GraphicsInterface graphics, PhysicsInterface physics) {
        this.moves = moves;
        this.graphics = graphics;
        this.physics = physics;
        this.transitions = new HashMap<>();
    }

    /**
     * Set a transition from this state to another state on an event.
     */
    public void setTransition(String event, StateInterface target) {
        this.transitions.put(event, target);
    }

    @Override
    public ImgInterface getCurrentSprite(int nowMs) {
        return graphics.getImg(); // Placeholder, replace with actual implementation
    }

    /**
     * Reset the state with a new command.
     */
    public void reset(CommandInterface cmd) {
        cmd.setTimestamp((int) System.currentTimeMillis());
        graphics.reset(cmd);
        physics.reset(cmd);
    }

    /**
     * Update the state based on current time.
     */
    @Override
    public StateInterface update(int nowMs) {
        graphics.update(nowMs); // ← חובה כדי לראות אנימציה
        CommandInterface newCmd = physics.update(getCommand(), nowMs);
        if (newCmd != null) {
            reset(newCmd); // ← הפעלה מחדש עם פקודה חדשה (כמו DONE)
        }
        return this;
    }

    /**
     * Get the next state after processing a command.
     */
    public StateInterface processCommand(CommandInterface cmd, int nowMs) {
        StateInterface res = transitions.get(cmd.getType());
        if (res == null) {
            return null;
        }
        res.reset(cmd);
        return res;
    }

    /**
     * Check if the state can transition.
     * Customise per state.
     */
    public boolean canTransition(int nowMs) {
        // ...existing code...
        return false;
    }

    /**
     * Get the current command for this state.
     */
    public CommandInterface getCommand() {
        // ...existing code...
        return command;
    }

    // Getters and setters
    @Override
    public GraphicsInterface getGraphics() {
        return graphics;
    }

    public void setGraphics(GraphicsInterface graphics) {
        this.graphics = graphics;
    }

    @Override
    public PhysicsInterface getPhysics() {
        return physics;
    }

    public void setPhysics(PhysicsInterface physics) {
        this.physics = physics;
    }

    @Override
    public Map<String, StateInterface> getTransitions() {
        return transitions;
    }

    @Override
    public void setTransitions(Map<String, StateInterface> transitions) {
        this.transitions = transitions;
    }

    @Override
    public MovesInterface getMoves() {
        return moves;
    }

    @Override
    public void setMoves(MovesInterface moves) {
        this.moves = moves;
    }

    @Override
    public StateType getType() {
        return type;
    }

    @Override
    public void setType(StateType type) {
        this.type = type;
    }

    @Override
    public void setCommand(CommandInterface cmd) {
        this.command = cmd;
        if (physics == null) {
            System.out.println("[DEBUG] physics is NULL inside setCommand for state: " + type.name());
        } else {
            physics.reset(cmd);
            System.out.println("[DEBUG] physics.reset(cmd) CALLED for state: " + type.name());
        }
    }

}