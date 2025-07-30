package it1engine.mock;

import it1engine.interfaces.ImgInterface;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;


/**
 * MockImg - Headless Img that just records calls for testing
 */
public class MockImg implements ImgInterface {
    
    // Static lists to record all calls across all instances (like Python class variables)
    private static List<Position> traj = new ArrayList<>();           // every drawOn() position
    private static List<TextCall> txtTraj = new ArrayList<>();        // every putText() call
    
    // Mock image data
    private String mockPixels;
    private int width;
    private int height;
    
    
    /**
     * Inner class to represent position coordinates
     */
    public static class Position {
        public final int x;
        public final int y;
        
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public String toString() {
            return String.format("(%d, %d)", x, y);
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Position position = (Position) obj;
            return x == position.x && y == position.y;
        }
    }
    
    /**
     * Inner class to represent text call information
     */
    public static class TextCall {
        public final Position position;
        public final String text;
        
        public TextCall(Position position, String text) {
            this.position = position;
            this.text = text;
        }
        
        public TextCall(int x, int y, String text) {
            this(new Position(x, y), text);
        }
        
        @Override
        public String toString() {
            return String.format("TextCall{pos=%s, text='%s'}", position, text);
        }
    }
    
    /**
     * Constructor - override, no real image loading needed
     * Equivalent to Python's __init__
     */
    public MockImg() {
        this.mockPixels = "MOCK-PIXELS";
        this.width = 1;
        this.height = 1;
    }
    
    /**
     * Mock implementation of read method
     * Equivalent to Python's read method
     */
    @Override
    public ImgInterface read(String path, int[] size, boolean keepAspect, int interpolation) {
        // Pretend size comes from file name for debug (like Python version)
        this.width = this.height = 1;
        // Just return this for chain-call compatibility
        return this;
    }
    
    /**
     * Override read method without parameters
     */
    @Override
    public ImgInterface read(String path) {
        return read(path, null, false, 0);
    }
    
    /**
     * Mock implementation that records draw calls
     * Equivalent to Python's draw_on method
     */
      /**
     * Adapted drawOn to match Img class signature
     */
    @Override
    public void drawOn(ImgInterface target, int xPix, int yPix) {
        // int xPix = cellX * cellW;
        // int yPix = cellY * cellH;
        // traj.add(new Position(xPix, yPix));
    }

    //   @Override
    // public void drawAt(ImgInterface board, int cellX, int cellY, int cellW, int cellH) {
    //     //
    //     // int xPix = cellX * cellW;
    //     // int yPix = cellY * cellH;
    //     // traj.add(new Position(xPix, yPix));
    // }

    
    /**
     * Mock implementation that records text calls
     * Equivalent to Python's put_text method
     */
    @Override
    public void putText(String txt, int x, int y, float fontSize, Color color, int thickness) {
        txtTraj.add(new TextCall(x, y, txt));
    }
    
    /**
     * Mock implementation - do nothing
     * Equivalent to Python's show method
     */
    @Override
    public void show() {
        // Do nothing in mock
    }
    
    /**
     * Mock implementation - always return true
     */
    @Override
    public boolean isLoaded() {
        return true; // Mock is always "loaded"
    }
    
    /**
     * Mock implementation - return mock size
     */
    @Override
    public int[] getSize() {
        return new int[]{width, height};
    }
    
    /**
     * Mock implementation of clone
     */
    @Override
    public ImgInterface clone() {
        return new MockImg(); // Return new mock instance
    }
    
    // ============= Test Helper Methods =============
    
    /**
     * Helper method for tests - reset all recorded calls
     * Equivalent to Python's reset classmethod
     */
    public static void reset() {
        traj.clear();
        txtTraj.clear();
    }
    
    /**
     * Get recorded trajectory for testing
     * Returns copy to prevent external modification
     */
    public static List<Position> getTraj() {
        return new ArrayList<>(traj);
    }
    
    /**
     * Get recorded text calls for testing
     * Returns copy to prevent external modification
     */
    public static List<TextCall> getTxtTraj() {
        return new ArrayList<>(txtTraj);
    }
    
    /**
     * Get trajectory as string for debugging
     */
    public static String getTrajString() {
        StringBuilder sb = new StringBuilder();
        sb.append("traj: [");
        for (int i = 0; i < traj.size(); i++) {
            sb.append(traj.get(i));
            if (i < traj.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * Get text trajectory as string for debugging
     */
    public static String getTxtTrajString() {
        StringBuilder sb = new StringBuilder();
        sb.append("txt_traj: [");
        for (int i = 0; i < txtTraj.size(); i++) {
            sb.append(txtTraj.get(i));
            if (i < txtTraj.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * Check if specific position was recorded
     */
    public static boolean hasPosition(int x, int y) {
        return traj.contains(new Position(x, y));
    }
    
    /**
     * Check if specific text call was recorded
     */
    public static boolean hasTextCall(int x, int y, String text) {
        for (TextCall call : txtTraj) {
            if (call.position.x == x && call.position.y == y && call.text.equals(text)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get number of recorded draw calls
     */
    public static int getDrawCount() {
        return traj.size();
    }
    
    /**
     * Get number of recorded text calls
     */
    public static int getTextCount() {
        return txtTraj.size();
    }


        // ==== BufferedImage stub methods for interface compatibility ====

    @Override
    public java.awt.image.BufferedImage getBufferedImage() {
        return null;
    }

    @Override
    public void setBufferedImage(java.awt.image.BufferedImage img) {
        // no-op
    }
}

    

