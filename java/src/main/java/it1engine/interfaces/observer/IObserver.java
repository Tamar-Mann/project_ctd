package it1engine.interfaces.observer;
import it1engine.interfaces.command.CommandInterface;

public interface IObserver {
    
    void Notify(CommandInterface cmd);
}



