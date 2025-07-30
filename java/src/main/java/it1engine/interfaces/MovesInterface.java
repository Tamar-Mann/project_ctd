package it1engine.interfaces;

import java.util.List;

import it1engine.impl.model.Moves.Pair;

/**
 * Interface for moves handling
 */
public interface MovesInterface {
    
    /**
     * Get all possible moves from a given position.
     */
    List<int[]> getMoves(int r, int c);

    boolean isValid(int[] srcCell, int[] dstCell, java.util.Set<Pair> occupiedCells);
}
