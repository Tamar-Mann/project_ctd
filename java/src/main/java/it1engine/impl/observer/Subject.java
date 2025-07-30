package it1engine.impl.observer;

import java.util.ArrayList;
import java.util.List;
import it1engine.interfaces.command.CommandInterface;
import it1engine.interfaces.observer.IObserver;
import it1engine.interfaces.observer.ISubject;


public class Subject implements ISubject {
    private final List<IObserver> observers = new ArrayList<>();

    @Override
    public void AddObserver(IObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void RemoveObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void NotifyAll(CommandInterface cmd) {
        for (IObserver observer : observers) {
            observer.Notify(cmd);
        }
    }
}
