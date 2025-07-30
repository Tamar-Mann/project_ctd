package it1engine.interfaces.command;

import it1engine.interfaces.ImgInterface;

public interface CommandListenerInterface {
    boolean supports(CommandInterface cmd);
    // void handle(CommandInterface cmd);
    void handle(CommandInterface cmd, ImgInterface canvas, long nowMs);

}
