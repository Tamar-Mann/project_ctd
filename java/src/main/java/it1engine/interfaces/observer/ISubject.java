package it1engine.interfaces.observer;

import it1engine.interfaces.command.CommandInterface;

public interface ISubject {
    
    void AddObserver(IObserver observer);
    void RemoveObserver(IObserver observer);
    void NotifyAll(CommandInterface cmd);
}
