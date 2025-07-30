package it1engine.interfaces;
import it1engine.impl.model.Board;
import it1engine.impl.model.Moves;
import it1engine.impl.model.Piece;
import it1engine.interfaces.command.CommandInterface;

/**
 * Interface for game pieces
 */
public interface PieceInterface {
    
    /**
     * Handle a command for this piece.
     */
    StateInterface onCommand(CommandInterface cmd, java.util.Map<Moves.Pair, java.util.List<Piece>> cell2piece,long nowMs);    
    /**
     * Reset the piece to idle state.
     */
    void reset(int startMs);
    
    /**
     * Update the piece state based on current time.
     */
    void update(int nowMs);
    
    /**
     * Draw the piece on the board with cooldown overlay.
     */
    // void drawOnBoard(BoardInterface board, int nowMs);
     void drawOnBoard(ImgInterface targetImg, BoardInterface board, int nowMs);
    
    // Basic piece information
    String getPieceId();
    void setPieceId(String pieceId);
    int[] getPosition();
     StateInterface getState();
     void setPosition(int[] position);

}
