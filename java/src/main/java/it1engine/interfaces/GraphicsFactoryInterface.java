package it1engine.interfaces;

import java.nio.file.Path;
import java.util.Map;

/**
 * Interface for graphics factory
 */
public interface GraphicsFactoryInterface {
    
    /**
     * Load graphics from sprites directory with configuration
     * @param spritesDir Path to sprites directory
     * @param cfg Configuration map
     * @param cellSize Cell size as [width, height]
     * @return Created graphics instance
     */
    GraphicsInterface load(Path spritesDir, Map<String, Object> cfg, int[] cellSize);
}
