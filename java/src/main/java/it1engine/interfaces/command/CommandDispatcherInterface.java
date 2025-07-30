package it1engine.interfaces.command;

import it1engine.interfaces.ImgInterface;

public interface CommandDispatcherInterface {
    void register(CommandListenerInterface listener);
void dispatch(CommandInterface cmd, ImgInterface canvas, long now);
}
