package it1engine.impl.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import it1engine.interfaces.BoardInterface;
import it1engine.interfaces.ImgInterface;
import it1engine.interfaces.InputHandlerInterface;

/**
 * Board class representing the game board
 */
public class Board implements BoardInterface {
    private int cellHPix; // Cell height in pixels
    private int cellWPix; // Cell width in pixels
    private int cellHM; // Cell height in meters
    private int cellWM; // Cell width in meters
    private int wCells; // Number of cells in width
    private int hCells; // Number of cells in height
    private ImgInterface img; // Board background image
    private JLabel label; // ← הוסף
    private JFrame frame; //

    /**
     * Constructor - equivalent to Python dataclass __init__
     */
    // public Board(int cellHPix, int cellWPix, int cellHM, int cellWM,
    // int wCells, int hCells, ImgInterface img) {
    // this.cellHPix = cellHPix;
    // this.cellWPix = cellWPix;
    // this.cellHM = cellHM;
    // this.cellWM = cellWM;
    // this.wCells = wCells;
    // this.hCells = hCells;
    // this.img = img;
    // }

    public Board(int cellHPix, int cellWPix, int cellHM, int cellWM,
            int wCells, int hCells, ImgInterface img) {
        System.out.println("Board created: cellWPix=" + cellWPix + ", cellHPix=" + cellHPix); // ← הוסף את זה

        this.cellHPix = cellHPix;
        this.cellWPix = cellWPix;
        this.cellHM = cellHM;
        this.cellWM = cellWM;
        this.wCells = wCells;
        this.hCells = hCells;
        int targetWidth = wCells * cellWPix;
        int targetHeight = hCells * cellHPix;
        BufferedImage original = img.getBufferedImage();
        if (original.getWidth() == targetWidth && original.getHeight() == targetHeight) {
            // אין צורך להקטין – שומרים כמות שהיא
            this.img = img;
        } else {
            // מבצעים שינוי גודל (scale) לתמונה שקיבלנו
            Image scaled = original.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resized.createGraphics();
            g2.drawImage(scaled, 0, 0, null);
            g2.dispose();
            this.img = new Img(resized);
        }
    }

    // @Override
    // public void repaint() {
    // if (label != null && img != null) {
    // BufferedImage buffer = img.getBufferedImage();
    // SwingUtilities.invokeLater(() -> {
    // label.setIcon(new ImageIcon(buffer));
    // label.repaint();
    // });
    // }
    // }

    public void repaint() {
        if (label != null && img != null) {
            BufferedImage buffer = img.getBufferedImage();
            SwingUtilities.invokeLater(() -> {
                label.setIcon(new ImageIcon(buffer));
                label.repaint();
            });
        }
    }

    public JLabel getLabel() {
        return label;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    /**
     * Clone the board with a copy of the image.
     */
    @Override
    public BoardInterface clone() {
        ImgInterface clonedImg = (this.img != null) ? this.img.clone() : null;
        return new Board(cellHPix, cellWPix, cellHM, cellWM, wCells, hCells, clonedImg);
    }

    /**
     * Get the board's dimensions in pixels and meters.
     */
    @Override
    public int[] toCell(String pos) {
        // Expect input like "e2"
        if (pos == null || pos.length() < 2)
            throw new IllegalArgumentException("Invalid position: " + pos);

        char colChar = pos.charAt(0);
        int row = Integer.parseInt(pos.substring(1));

        int col = colChar - 'a'; // e.g., 'e' → 4
        int rowIndex = hCells - row; // row 1 is at bottom

        if (col < 0 || col >= wCells || rowIndex < 0 || rowIndex >= hCells) {
            throw new IllegalArgumentException("Position out of bounds: " + pos);
        }

        return new int[] { col, rowIndex };
    }

    @Override
    public double[] cellToM(Moves.Pair cell) {
        System.out.println("cellToM: cell.r=" + cell.r + ", cell.c=" + cell.c +
                ", cellWPix=" + cellWPix + ", cellHPix=" + cellHPix);
        double[] result = new double[] {
                cell.c * cellWPix,
                cell.r * cellHPix
        };
        System.out.println("cellToM result: [" + result[0] + ", " + result[1] + "]");
        return result;
    }

    @Override
    public Moves.Pair mToCell(double[] posM) {
        if (posM == null) {
            return new Moves.Pair(0, 0);
        }

        int col = (int) (posM[0] / cellWPix);
        int row = (int) (posM[1] / cellHPix);

        // וודא שהמיקום בגבולות הלוח
        col = Math.max(0, Math.min(col, wCells - 1));
        row = Math.max(0, Math.min(row, hCells - 1));

        return new Moves.Pair(row, col);
    }

    @Override
    public int[] toPixels(float[] cell) {
        int x = Math.round(cell[0] * cellWPix);
        int y = Math.round(cell[1] * cellHPix);
        return new int[] { x, y };
    }

    // Getters - equivalent to Python dataclass auto-generated getters
    @Override
    public int getCellHPix() {
        return cellHPix;
    }

    @Override
    public int getCellWPix() {
        return cellWPix;
    }

    @Override
    public int getCellHM() {
        return cellHM;
    }

    @Override
    public int getCellWM() {
        return cellWM;
    }

    @Override
    public int getWCells() {
        return wCells;
    }

    @Override
    public int getHCells() {
        return hCells;
    }

    @Override
    public ImgInterface getImg() {
        return img;
    }

    // Setters - equivalent to Python dataclass auto-generated setters
    @Override
    public void setCellHPix(int cellHPix) {
        this.cellHPix = cellHPix;
    }

    @Override
    public void setCellWPix(int cellWPix) {
        this.cellWPix = cellWPix;
    }

    @Override
    public void setCellHM(int cellHM) {
        this.cellHM = cellHM;
    }

    @Override
    public void setCellWM(int cellWM) {
        this.cellWM = cellWM;
    }

    @Override
    public void setWCells(int wCells) {
        this.wCells = wCells;
    }

    @Override
    public void setHCells(int hCells) {
        this.hCells = hCells;
    }

    @Override
    public void setImg(ImgInterface img) {
        this.img = img;
    }

    public void showWithInput(KeyboardInputHandler inputHandler) {
        SwingUtilities.invokeLater(() -> {
            int width = getWCells() * getCellWPix();
            int height = getHCells() * getCellHPix();

            BufferedImage raw = img.getBufferedImage();

            // הוסף ריפוד של 50 פיקסלים מכל צד - לא יותר מדי
            int paddedWidth = width + 100; // 50 פיקסלים מכל צד
            int paddedHeight = height + 100; // 50 פיקסלים מכל צד

            BufferedImage scaled = new BufferedImage(paddedWidth, paddedHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = scaled.createGraphics();

            // צייר את התמונה המקורית במרכז התמונה המורחבת
            int offsetX = 50;
            int offsetY = 50;
            g2d.drawImage(raw, offsetX, offsetY, width, height, null);
            g2d.dispose();

            label = new JLabel(new ImageIcon(scaled));
            frame = new JFrame("Chess Board");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(label);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            frame.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyPressed(java.awt.event.KeyEvent e) {
                    String key = switch (e.getKeyCode()) {
                        case java.awt.event.KeyEvent.VK_UP -> "UP";
                        case java.awt.event.KeyEvent.VK_DOWN -> "DOWN";
                        case java.awt.event.KeyEvent.VK_LEFT -> "LEFT";
                        case java.awt.event.KeyEvent.VK_RIGHT -> "RIGHT";
                        case java.awt.event.KeyEvent.VK_ENTER -> "ENTER";
                        case java.awt.event.KeyEvent.VK_SPACE -> "SPACE";
                        case java.awt.event.KeyEvent.VK_W -> "W";
                        case java.awt.event.KeyEvent.VK_A -> "A";
                        case java.awt.event.KeyEvent.VK_S -> "S";
                        case java.awt.event.KeyEvent.VK_D -> "D";
                        default -> null;
                    };
                    if (key != null) {
                        inputHandler.inputHandler(key, (int) System.currentTimeMillis());
                    }
                }
            });
        });
    }

    public int[] cellStringToCoords(String cell) {
        if (cell == null || cell.length() != 2) {
            throw new IllegalArgumentException("Invalid cell format: " + cell);
        }

        char colChar = Character.toUpperCase(cell.charAt(0));
        char rowChar = cell.charAt(1);

        int col = colChar - 'A';
        int row = Character.getNumericValue(rowChar) - 1;

        if (col < 0 || col >= wCells || row < 0 || row >= hCells) {
            throw new IllegalArgumentException("Cell out of board bounds: " + cell);
        }
        return new int[] { col, row };
    }

    @Override
    public void drawCursorOverlay(String playerId, int[] cell, ImgInterface boardImg) {
        if (!(boardImg instanceof Img)) {
            throw new IllegalArgumentException("boardImg must be instance of Img");
        }

        BufferedImage buffer = ((Img) boardImg).getBufferedImage();
        Graphics2D g = buffer.createGraphics();

        Color color = playerId.equals("P1") ? new Color(0, 0, 255, 100) : new Color(0, 255, 0, 100);
        int x = cell[1] * getCellWPix();
        int y = cell[0] * getCellHPix();

        g.setColor(color);
        g.fillRect(x, y, getCellWPix(), getCellHPix());
        g.dispose();
    }

}