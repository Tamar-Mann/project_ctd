package it1engine.impl.factory;

import it1engine.interfaces.GraphicsFactoryInterface;
import it1engine.interfaces.GraphicsInterface;
import it1engine.impl.model.Graphics;

import java.nio.file.Path;
import java.util.Map;

/**
 * GraphicsFactory class for creating graphics instances
 */

public class GraphicsFactory implements GraphicsFactoryInterface {

    @Override
    public GraphicsInterface load(Path spritesDir, Map<String, Object> cfg, int[] cellSize) {
        float fps = 6.0f;
        boolean loop = true;

        if (cfg != null) {
            Object fpsObj = cfg.get("fps");
            if (fpsObj instanceof Number) {
                fps = ((Number) fpsObj).floatValue();
            }

            Object loopObj = cfg.get("loop");
            if (loopObj instanceof Boolean) {
                loop = (Boolean) loopObj;
            } else if (loopObj instanceof String) {
                loop = Boolean.parseBoolean((String) loopObj);
            }
        }

        return new Graphics(spritesDir, null, loop, fps);
    }
}

