package it1engine.interfaces;

import it1engine.impl.model.Moves;

/**
 * Interface for piece factory
 */
public interface PieceFactoryInterface {
    
    /**
     * Create a piece of the specified type at the given cell.
     */
    PieceInterface createPiece(String code, Moves.Pair cell);
}
