package it1engine.interfaces.command;

import java.util.List;

public interface CommandQueueInterface {
    void add(CommandInterface cmd);
    CommandInterface poll();
    CommandInterface peek();
    boolean isEmpty();
    void clear();
    int size();
    List<CommandInterface> getAll();
    List<CommandInterface> popUntil(int nowMs);
}
