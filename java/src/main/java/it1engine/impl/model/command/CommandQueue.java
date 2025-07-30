package it1engine.impl.model.command;
import it1engine.interfaces.command.CommandInterface;
import it1engine.interfaces.command.CommandQueueInterface;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CommandQueue implements CommandQueueInterface {

    private final Queue<CommandInterface> queue;

    public CommandQueue() {
        this.queue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void add(CommandInterface cmd) {
        if (cmd == null) throw new IllegalArgumentException("Command cannot be null");
        queue.add(cmd);
    }

    @Override
    public CommandInterface poll() {
        return queue.poll();
    }

    @Override
    public CommandInterface peek() {
        return queue.peek();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public void clear() {
        queue.clear();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public List<CommandInterface> getAll() {
        return new ArrayList<>(queue);
    }

    @Override
    public List<CommandInterface> popUntil(int nowMs) {
        List<CommandInterface> result = new ArrayList<>();
        while (!queue.isEmpty() && queue.peek().getTimestamp() <= nowMs) {
            result.add(queue.poll());
        }
        return result;
    }
}
