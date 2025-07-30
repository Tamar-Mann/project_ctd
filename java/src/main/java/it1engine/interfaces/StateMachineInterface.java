package it1engine.interfaces;

import it1engine.interfaces.command.CommandInterface;

public interface StateMachineInterface {
    void reset(CommandInterface cmd);
    StateMachineInterface update(long nowMs);
    StateMachineInterface processCommand(CommandInterface cmd, long nowMs);
    void setTransition(String event, StateMachineInterface nextState);
}
