package it1engine.interfaces;

import java.util.List;
import java.util.Map;

/**
 * Interface for game engine
 */
public interface GameInterface {
    
    // Main game control methods
    void run();
    
    // Game state methods
    int gameTimeMs();
    BoardInterface cloneBoard();
    
    // Input handling
    void startUserInputThread();
    
    // Game loop components
    boolean isWin();
    void announceWin();
    
    // Piece management
    Map<String, PieceInterface> getPieces();
    void addPiece(PieceInterface piece);
    void removePiece(String pieceId);
    
    // Board access
    BoardInterface getBoard();
    void setBoard(BoardInterface board);
}
