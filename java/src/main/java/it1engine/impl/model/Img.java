package it1engine.impl.model;

import it1engine.interfaces.ImgInterface;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Img class for image operations
 */
public class Img implements ImgInterface {

    private BufferedImage img;
    
    // Interpolation constants (similar to OpenCV)
    public static final int INTER_AREA = 0;
    public static final int INTER_LINEAR = 1;
    public static final int INTER_CUBIC = 2;
    
    /**
     * Constructor - equivalent to Python's __init__
     */
    public Img(BufferedImage image) {
        this.img = image;
    }

    public Img() {
        this.img = null;
    }
    
    /**
     * Load image from path and optionally resize.
     */
    @Override
    public ImgInterface read(String path, int[] size, boolean keepAspect, int interpolation) {
        try {
            this.img = ImageIO.read(new File(path));
            if (this.img == null) {
                throw new RuntimeException("Cannot load image: " + path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot load image: " + path, e);
        }
        
        // Resize if size is specified
        if (size != null && size.length >= 2) {
            int targetW = size[0];
            int targetH = size[1];
            int currentW = img.getWidth();
            int currentH = img.getHeight();
            
            int newW, newH;
            
            if (keepAspect) {
                // Preserve aspect ratio - scale to fit within target size
                double scale = Math.min((double)targetW / currentW, (double)targetH / currentH);
                newW = (int)(currentW * scale);
                newH = (int)(currentH * scale);
            } else {
                // Resize exactly to target size
                newW = targetW;
                newH = targetH;
            }
            
            // Create resized image
            BufferedImage resized = new BufferedImage(newW, newH, img.getType());
            Graphics2D g2d = resized.createGraphics();
            
            // Set rendering hints based on interpolation
            setRenderingHints(g2d, interpolation);
            
            g2d.drawImage(img, 0, 0, newW, newH, null);
            g2d.dispose();
            
            this.img = resized;
        }
        
        return this;
    }
    
    /**
     * Load image with default parameters.
     */
@Override
public ImgInterface read(String path) {
    try {
        // חייב להיות נתיב יחסי מתוך resources
        InputStream stream = getClass().getClassLoader().getResourceAsStream(path);
        if (stream == null) {
            throw new RuntimeException("Image not found in classpath: " + path);
        }

        this.img = ImageIO.read(stream);
        if (this.img == null) {
            throw new RuntimeException("Failed to load image from stream: " + path);
        }

        return this;
    } catch (IOException e) {
        throw new RuntimeException("Error loading image: " + path, e);
    }
}



    
    /**
     * Draw this image on another image at specified position.
     */
    // @Override
    // public void drawOn(ImgInterface board, int cellX, int cellY, int cellW, int cellH) {
    //     if (img == null || board.getBufferedImage() == null)
    //         throw new IllegalStateException("Both images must be loaded.");
    //     // מיקום פיקסלי של התא
    //     int xPix = cellX * cellW;
    //     int yPix = cellY * cellH;
    //     // :white_check_mark: הדפסות מידע
    //     System.out.printf(":large_green_circle: Piece - Placing on board at pixel coords: (%d, %d)%n", xPix, yPix);
    //     System.out.printf(":large_green_circle: Piece - Resizing to: %dx%d%n", cellW, cellH);
    //     System.out.printf(":large_green_circle: Piece - Board image size: %dx%d%n", board.getBufferedImage().getWidth(), board.getBufferedImage().getHeight());
    //     System.out.printf(":large_green_circle: Piece - Actual image input size: %dx%d%n", img.getWidth(), img.getHeight());
    //     // שינוי גודל התמונה לגודל התא (ביחס נכון)
    //     Image tmp = img.getScaledInstance(cellW, cellH, Image.SCALE_SMOOTH);
    //     BufferedImage resized = new BufferedImage(cellW, cellH, BufferedImage.TYPE_INT_ARGB);
    //     Graphics2D g2 = resized.createGraphics();
    //     g2.drawImage(tmp, 0, 0, null);
    //     g2.dispose();
    //     // ציור על הלוח במיקום המדויק
    //     Graphics2D g = board.getBufferedImage().createGraphics();
    //     g.setComposite(AlphaComposite.SrcOver);
    //     g.drawImage(resized, yPix, xPix, null);
    //     g.dispose();
    // }

    


    // Draw resized image at absolute pixel position (xPix, yPix)
public void drawOn(ImgInterface target, int xPix, int yPix) {
    if (img == null || target.getBufferedImage() == null)
        throw new IllegalStateException("Both images must be loaded.");

    int wPix = img.getWidth();
    int hPix = img.getHeight();

    Image tmp = img.getScaledInstance(wPix, hPix, Image.SCALE_SMOOTH);
    BufferedImage resized = new BufferedImage(wPix, hPix, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = resized.createGraphics();
    g2.drawImage(tmp, 0, 0, null);
    g2.dispose();

    Graphics2D g = target.getBufferedImage().createGraphics();
    g.setComposite(AlphaComposite.SrcOver);
    g.drawImage(resized, xPix, yPix, null);
    g.dispose();
}



    // public void drawAt(ImgInterface target, int xPix, int yPix, int wPix, int hPix) {
    //     if (img == null || target.getBufferedImage() == null)
    //         throw new IllegalStateException("Both images must be loaded.");
    //     Image tmp = img.getScaledInstance(wPix, hPix, Image.SCALE_SMOOTH);
    //     BufferedImage resized = new BufferedImage(wPix, hPix, BufferedImage.TYPE_INT_ARGB);
    //     Graphics2D g2 = resized.createGraphics();
    //     g2.drawImage(tmp, 0, 0, null);
    //     g2.dispose();
    //     Graphics2D g = target.getBufferedImage().createGraphics();
    //     g.setComposite(AlphaComposite.SrcOver);
    //     g.drawImage(resized, xPix, yPix, null);
    //     g.dispose();
    //     System.out.printf(":art: Img.drawAt → drawing at (%d, %d), size %dx%d\n", xPix, yPix, wPix, hPix);
    // }

    /**
     * Put text on the image
     * Equivalent to Python's put_text method
     */
    @Override
    public void putText(String txt, int x, int y, float fontSize, Color color, int thickness) {
        if (this.img == null) {
            throw new IllegalStateException("Image is not loaded");
        }
        
        Graphics2D g2d = this.img.createGraphics();
        
        // Set rendering hints for better text quality
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Set font
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, (int)fontSize);
        g2d.setFont(font);
        
        // Set color
        g2d.setColor(color);
        
        // Set stroke for thickness (approximate)
        if (thickness > 1) {
            g2d.setStroke(new BasicStroke(thickness));
        }
        
        // Draw text
        g2d.drawString(txt, x, y);
        
        g2d.dispose();
    }
    
    /**
     * Display the image in a window
     * Equivalent to Python's show method
     */
    @Override
    public void show() {
        if (this.img == null) {
            throw new IllegalStateException("Image is not loaded");
        }
        
        System.out.println("Creating JFrame window...");
        JFrame frame = new JFrame("Image");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JLabel label = new JLabel(new ImageIcon(this.img));
        frame.add(label);
        
        frame.pack();
        System.out.println("About to show window...");
        frame.setVisible(true);
        System.out.println("Window is now visible!");
        
        // Wait for user to close window (similar to cv2.waitKey(0))
        // This is a simplified version - in a real application you might want more control
        try {
            Thread.sleep(5000); // מחכה 5 שניות כדי שנוכל לראות את החלון
            System.out.println("Window should be visible for 5 seconds...");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Check if image is loaded
     */
    @Override
    public boolean isLoaded() {
        return this.img != null;
    }
    
    /**
     * Get image dimensions
     */
    @Override
    public int[] getSize() {
        if (this.img == null) {
            return null;
        }
        return new int[]{img.getWidth(), img.getHeight()};
    }
    
    /**
     * Clone the image
     * Equivalent to creating a copy in Python
     */
    @Override
    public ImgInterface clone() {
        Img cloned = new Img();
        if (this.img != null) {
            cloned.img = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            Graphics2D g2d = cloned.img.createGraphics();
            g2d.drawImage(this.img, 0, 0, null);
            g2d.dispose();
        }
        return cloned;
    }
    
    /**
     * Set rendering hints based on interpolation method
     */
    private void setRenderingHints(Graphics2D g2d, int interpolation) {
        switch (interpolation) {
            case INTER_LINEAR:
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                break;
            case INTER_CUBIC:
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                break;
            case INTER_AREA:
            default:
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                break;
        }
        
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
    
    /**
     * Get the underlying BufferedImage (for advanced usage)
     */
    public BufferedImage getBufferedImage() {
        return this.img;
    }
    
    /**
     * Set the underlying BufferedImage (for advanced usage)
     */
    public void setBufferedImage(BufferedImage img) {
        this.img = img;
    }

}
