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

    public State(MovesInterface moves, GraphicsInterface graphics, PhysicsInterface physics) {
        this.moves = moves;
        this.graphics = graphics;
        this.physics = physics;
        this.transitions = new HashMap<>();
    }

    public void setTransition(String event, StateInterface target) {
        this.transitions.put(event, target);
    }

    @Override
    public ImgInterface getCurrentSprite(int nowMs) {
        return graphics.getImg();
    }

    @Override
    public void reset(CommandInterface cmd) {
        cmd.setTimestamp((int) System.currentTimeMillis());
        this.command = cmd;
        graphics.reset(cmd);
        physics.reset(cmd);
    }

    @Override
    public StateInterface update(int nowMs) {
        graphics.update(nowMs);
        physics.update(nowMs);  // <-- מותאם לחתימה החדשה של abstract physics
        return this;
    }

    @Override
    public StateInterface processCommand(CommandInterface cmd, int nowMs) {
        StateInterface res = transitions.get(cmd.getType());
        if (res == null) return null;
        res.reset(cmd);
        return res;
    }

    @Override
    public boolean canTransition(int nowMs) {
        return false;
    }

    @Override
    public CommandInterface getCommand() {
        return command;
    }

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
        if (physics != null) {
            physics.reset(cmd);
        }
    }
}
