package it1engine.interfaces.command;

import java.util.List;

import it1engine.impl.model.Moves;

public interface CommandProducerInterface {
 CommandInterface createMoveCommand(String pieceId, Moves.Pair from, Moves.Pair to, int timestamp);    CommandInterface createJumpCommand(String pieceId, Moves.Pair pair, int timestamp);
    CommandInterface createIdleCommand(String pieceId, int timestamp);
    CommandInterface createLongRestCommand(String pieceId, int timestamp);
    CommandInterface createShortRestCommand(String pieceId, int timestamp);
    CommandInterface createGenericCommand(String pieceId, String type, Moves.Pair params, int timestamp);
   
}
