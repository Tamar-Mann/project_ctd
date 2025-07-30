package it1engine.interfaces;
import java.util.Map;

import it1engine.enums.StateType;
import it1engine.impl.model.Img;
import it1engine.interfaces.command.CommandInterface;

/**
 * Interface for piece states
 */
public interface StateInterface {
    
    /**
     * Set a transition from this state to another state on an event.
     */
    void setTransition(String event, StateInterface target);
    
    /**
     * Reset the state with a new command.
     */
    void reset(CommandInterface cmd);
    
    /**
     * Update the state based on current time.
     */
    // דוגמה לשינוי בממשק:
    StateInterface update(int nowMs);

    PhysicsInterface getPhysics();
    
    /**
     * Get the next state after processing a command.
     */
    StateInterface processCommand(CommandInterface cmd, int nowMs);

    /**
     * Check if the state can transition.
     */
    boolean canTransition(int nowMs);    
    
    /**
     * Get the current command for this state.
     */
    CommandInterface getCommand();

    ImgInterface getCurrentSprite(int nowMs);

    MovesInterface getMoves();

    void setMoves(MovesInterface moves);

    void setTransitions(Map<String, StateInterface> transitions);

    Map<String, StateInterface> getTransitions();

    void setType(StateType type);
    StateType getType();


     GraphicsInterface getGraphics();

    void setCommand(CommandInterface cmd);

}
