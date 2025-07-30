package it1engine.interfaces;

import java.util.Map;

/**
 * Interface for physics factory
 */
public interface PhysicsFactoryInterface {
    
    /**
     * Create a physics object with the given configuration.
     */
    PhysicsInterface create(int[] startCell, Map<String, Object> cfg);
}
