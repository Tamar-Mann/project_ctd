package it1engine.interfaces;

import java.awt.Color;
import java.awt.image.BufferedImage;

import it1engine.impl.model.Img;

/**
 * Interface for image operations
 */
public interface ImgInterface {

    /**
     * Load image from path and optionally resize
     */
    ImgInterface read(String path, int[] size, boolean keepAspect, int interpolation);

    /**
     * Load image with default settings
     */
    ImgInterface read(String path);

    /**
     * Draw this image onto another image
     */
    // void drawOn(ImgInterface board, int cellX, int cellY, int cellW, int cellH) ;
    // void drawAt(ImgInterface target, int xPix, int yPix, int wPix, int hPix) ;
    void drawOn(ImgInterface target, int xPix, int yPix);

    /**
     * Put text on the image
     */
    void putText(String txt, int x, int y, float fontSize, Color color, int thickness);

    /**
     * Show the image in a window
     */
    void show();

    /**
     * Check if image is loaded
     */
    boolean isLoaded();

    /**
     * Get image dimensions
     */
    int[] getSize();

    /**
     * Clone the image
     */
    ImgInterface clone();

    /**
     * Get the underlying BufferedImage (advanced usage)
     */
    BufferedImage getBufferedImage();

    /**
     * Set the underlying BufferedImage (advanced usage)
     */
    void setBufferedImage(BufferedImage img);
}
