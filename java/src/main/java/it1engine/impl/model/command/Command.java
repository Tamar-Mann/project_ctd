package it1engine.impl.model.command;
import it1engine.interfaces.command.CommandInterface;
import it1engine.interfaces.observer.ISubject;
import it1engine.enums.*;
import it1engine.impl.model.Moves;
import it1engine.impl.observer.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Command implements CommandInterface {
    private int timestamp;
    private String pieceId;
    private CommandType type;
    private List<Moves.Pair> params;


    public Command(int timestamp, String pieceId, CommandType type, List<Moves.Pair> params) {
        if (pieceId == null || type == null)
            throw new IllegalArgumentException("pieceId and type must not be null");
        this.timestamp = timestamp;
        this.pieceId = pieceId;
        this.type = type;
        this.params = (params != null) ? new ArrayList<Moves.Pair>(params) : new ArrayList<Moves.Pair>();
    }

    public Command(int timestamp, String pieceId, CommandType type) {
        this(timestamp, pieceId, type, new ArrayList<>());
    }

    @Override
    public CommandInterface clone() {
        return new Command(timestamp, pieceId, type, new ArrayList<>(params));
    }

    @Override public int getTimestamp() { return timestamp; }
    @Override public String getPieceId() { return pieceId; }
    @Override public String getType() { return type.name(); }
    @Override public List<Moves.Pair> getParams() { return new ArrayList<Moves.Pair>(params); }

    public CommandType getCommandType() { return type; }

    @Override public void setTimestamp(int timestamp) { this.timestamp = timestamp; }
    @Override public void setPieceId(String pieceId) { this.pieceId = pieceId; }
    @Override public void setType(String typeStr) {
        this.type = CommandType.valueOf(typeStr.toUpperCase());
    }

    public void setCommandType(CommandType type) {
        this.type = type;
    }

    @Override public void setParams(List<Moves.Pair> params) {
        this.params = (params != null) ? new ArrayList<>(params) : new ArrayList<>();
    }
    
    @Override
    public void addParam(Moves.Pair param) {
        this.params.add(param);
    }

    @Override
    public String toString() {
        return String.format("Command{timestamp=%d, pieceId='%s', type='%s', params=%s}",
                timestamp, pieceId, type.name(), params);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Command)) return false;
        Command other = (Command) obj;
        return timestamp == other.timestamp &&
                pieceId.equals(other.pieceId) &&
                type == other.type &&
                params.equals(other.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, pieceId, type, params);
    }
}
