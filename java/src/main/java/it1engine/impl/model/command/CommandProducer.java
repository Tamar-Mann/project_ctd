package it1engine.impl.model.command;

import it1engine.interfaces.command.CommandInterface;
import it1engine.interfaces.command.CommandProducerInterface;
import it1engine.enums.CommandType;
import it1engine.impl.model.Moves;

import java.util.List;

/**
 * Produces Command instances from game input or events.
 */
public class CommandProducer implements CommandProducerInterface {

    @Override
    public CommandInterface createMoveCommand(String pieceId, Moves.Pair from, Moves.Pair to, int timestamp) {
        return new Command(timestamp, pieceId, CommandType.MOVE, List.of(from, to));
    }

    @Override
    public CommandInterface createJumpCommand(String pieceId, Moves.Pair pair, int timestamp) {
        return new Command(timestamp, pieceId, CommandType.JUMP, List.of(pair));
    }

    @Override
    public CommandInterface createIdleCommand(String pieceId, int timestamp) {
        return new Command(timestamp, pieceId, CommandType.IDLE);
    }

    @Override
    public CommandInterface createLongRestCommand(String pieceId, int timestamp) {
        return new Command(timestamp, pieceId, CommandType.REST);
    }

    @Override
    public CommandInterface createShortRestCommand(String pieceId, int timestamp) {
        return new Command(timestamp, pieceId, CommandType.REST);
    }

    @Override
    public CommandInterface createGenericCommand(String pieceId, String type, Moves.Pair params, int timestamp) {
        CommandType cmdType = CommandType.valueOf(type.toUpperCase());
        return new Command(timestamp, pieceId, cmdType, List.of(params));
    }
}
