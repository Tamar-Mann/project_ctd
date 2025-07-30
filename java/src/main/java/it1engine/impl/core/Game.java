// package it1engine.impl.core;

// import it1engine.interfaces.*;
// import it1engine.interfaces.command.CommandInterface;

// import java.util.*;
// import java.util.concurrent.BlockingQueue;
// import java.util.concurrent.LinkedBlockingQueue;

// /**
//  * Game class representing the main game engine
//  * Java equivalent of Python's Game class
//  */
// public class Game implements GameInterface {
    
//     // Game state
//     private Map<String, PieceInterface> pieces;
//     private BoardInterface board;
//     private BlockingQueue<CommandInterface> userInputQueue;
//     private int gameStartTime;
//     private volatile boolean running;
//     private final int gameStartNano;

    

    
//     /**
//      * Constructor - equivalent to Python's __init__
//      */
//     public Game(List<PieceInterface> pieces, BoardInterface board) {
//         this.pieces = new HashMap<>();
//         for (PieceInterface piece : pieces) {
//             this.pieces.put(piece.getPieceId(), piece);
//         }
//         this.board = board;
//         this.userInputQueue = new LinkedBlockingQueue<>();
//         this.gameStartTime = (int) System.currentTimeMillis();
//         this.running = false;
//         this.gameStartNano = (int) System.nanoTime();
//     }
    
//     /**
//      * Return the current game time in milliseconds
//      * Equivalent to Python's game_time_ms()
//      */
//     @Override
//     public int gameTimeMs() {
//         return (int) (System.currentTimeMillis() - gameStartTime);
//     }
    
//     /**
//      * Return a brand-new Board wrapping a copy of the background pixels
//      * Equivalent to Python's clone_board()
//      */
//     @Override
//     public BoardInterface cloneBoard() {
//         return board.clone();
//     }
    
//     /**
//      * Start the user input thread for mouse handling
//      * Equivalent to Python's start_user_input_thread()
//      */
//     @Override
//     public void startUserInputThread() {
//         Thread inputThread = new Thread(() -> {
//             // TODO: Implement mouse input handling
//             // This would handle mouse events and add commands to userInputQueue
//         });
//         inputThread.setDaemon(true);
//         inputThread.start();
//     }
    
//     /**
//      * Main game loop
//      * Equivalent to Python's run() method
//      */
//     @Override
//     public void run() {
//         startUserInputThread();
        
//         int startMs = gameTimeMs();
//         for (PieceInterface piece : pieces.values()) {
//             piece.reset(startMs);
//         }
        
//         running = true;
        
//         // Main loop
//         while (running && !isWin()) {
//             int now = gameTimeMs();

//             // (1) Update physics & animations
//             for (PieceInterface piece : pieces.values()) {
//                 piece.update(now);
//             }
            
//             // (2) Handle queued Commands from input thread
//             CommandInterface cmd;
//             while ((cmd = userInputQueue.poll()) != null) {
//                 processInput(cmd);
//             }
            
//             // (3) Draw current position
//             draw();
//             if (!show()) {  // returns false if user closed window
//                 break;
//             }
            
//             // (4) Detect captures
//             resolveCollisions();
            
//             // Small delay to prevent excessive CPU usage
//             try {
//                 Thread.sleep(16); // ~60 FPS
//             } catch (InterruptedException e) {
//                 Thread.currentThread().interrupt();
//                 break;
//             }
//         }
        
//         announceWin();
//         cleanup();
//     }
    
//     /**
//      * Process input command
//      * Equivalent to Python's _process_input()
//      */
//     private void processInput(CommandInterface cmd) {
//         PieceInterface piece = pieces.get(cmd.getPieceId());
//         if (piece != null) {
//             piece.onCommand(cmd, gameTimeMs());
//         }
//     }
    
//     /**
//      * Draw the current game state
//      * Equivalent to Python's _draw()
//      */
//     private void draw() {
//         // TODO: Implement drawing logic
//         // This would draw the board and all pieces
//     }
    
//     /**
//      * Show the current frame and handle window events
//      * Equivalent to Python's _show()
//      */
//     private boolean show() {
//         // TODO: Implement window showing and event handling
//         // Returns false if user wants to quit
//         return running;
//     }
    
//     /**
//      * Resolve piece collisions and captures
//      * Equivalent to Python's _resolve_collisions()
//      */
//     private void resolveCollisions() {
//         // TODO: Implement collision detection and resolution
//     }
    
//     /**
//      * Check if the game has ended
//      * Equivalent to Python's _is_win()
//      */
//     @Override
//     public boolean isWin() {
//         // TODO: Implement win condition checking
//         return false;
//     }
    
//     /**
//      * Announce the winner
//      * Equivalent to Python's _announce_win()
//      */
//     @Override
//     public void announceWin() {
//         // TODO: Implement win announcement
//         System.out.println("Game ended!");
//     }
    
//     /**
//      * Cleanup resources
//      */
//     private void cleanup() {
//         // TODO: Cleanup window resources (equivalent to cv2.destroyAllWindows())
//         running = false;
//     }
    
//     /**
//      * Stop the game
//      */
//     public void stop() {
//         running = false;
//     }
    
//     // Getters and setters for interface compliance
//     @Override
//     public Map<String, PieceInterface> getPieces() {
//         return new HashMap<>(pieces);
//     }
    
//     @Override
//     public void addPiece(PieceInterface piece) {
//         pieces.put(piece.getPieceId(), piece);
//     }
    
//     @Override
//     public void removePiece(String pieceId) {
//         pieces.remove(pieceId);
//     }
    
//     @Override
//     public BoardInterface getBoard() {
//         return board;
//     }
    
//     @Override
//     public void setBoard(BoardInterface board) {
//         this.board = board;
//     }
    
//     /**
//      * Add command to input queue (for external input handling)
//      */
//     public void addInputCommand(CommandInterface cmd) {
//         userInputQueue.offer(cmd);
//     }
// }
