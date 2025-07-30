package it1engine.impl.model.command;

import it1engine.interfaces.ImgInterface;
import it1engine.interfaces.command.CommandDispatcherInterface;
import it1engine.interfaces.command.CommandInterface;
import it1engine.interfaces.command.CommandListenerInterface;

import java.util.ArrayList;
import java.util.List;

public class CommandDispatcher implements CommandDispatcherInterface {

    private final List<CommandListenerInterface> listeners = new ArrayList<>();

    @Override
    public void register(CommandListenerInterface listener) {
        listeners.add(listener);
    }

    @Override
    public void dispatch(CommandInterface cmd, ImgInterface canvas, long now) {

        System.out.println("hiiiiiiiiiiiiiiiiiii");
        for (CommandListenerInterface listener : listeners) {
            if (listener.supports(cmd)) {
                listener.handle(cmd, canvas, now);
                return;
            }
        }
        throw new IllegalStateException("No listener found for command: " + cmd.getType());
    }

}
