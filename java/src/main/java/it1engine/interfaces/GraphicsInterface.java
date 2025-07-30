package it1engine.interfaces;

import it1engine.interfaces.command.CommandInterface;
import java.nio.file.Path;

/**
 * Interface for graphics rendering and animation
 */
public interface GraphicsInterface {
    
    // Animation control methods
    void reset(CommandInterface cmd);
    void update(long nowMs);
    
    // Image retrieval
    ImgInterface getImg();
    
    // Graphics state
    boolean isLooping();
    void setLooping(boolean loop);
    
    float getFps();
    void setFps(float fps);
    
    Path getSpritesFolder();
    BoardInterface getBoard();
    
    /**
     * Create a shallow copy of the graphics object
     * @return Cloned graphics instance
     */
    GraphicsInterface copy();
}
