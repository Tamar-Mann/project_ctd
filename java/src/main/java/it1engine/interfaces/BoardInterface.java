package it1engine.interfaces;

import java.awt.Graphics2D;

import it1engine.impl.model.KeyboardInputHandler;
import it1engine.impl.model.Moves;

/**
 * Interface for game board representation
 */
public interface BoardInterface {
    
    // Getters for board dimensions
    int getCellHPix();
    int getCellWPix();
    int getCellHM();
    int getCellWM();
    int getWCells();
    int getHCells();
    ImgInterface getImg();
    
    // Setters for board dimensions
    void setCellHPix(int cellHPix);
    void setCellWPix(int cellWPix);
    void setCellHM(int cellHM);
    void setCellWM(int cellWM);
    void setWCells(int wCells);
    void setHCells(int hCells);
    void setImg(ImgInterface img);
    
    /**
     * Clone the board with a copy of the image
     * @return Cloned board instance
     */
    BoardInterface clone();
    int[] toCell(String pos);
    int[] toPixels(float[] cell);

    double[] cellToM(Moves.Pair cell);

    // void show();
    // void drawOnBackground(BackGroundInterface background);
    // void show();
    int[] cellStringToCoords(String cell);

    void drawCursorOverlay(String playerId, int[] cell, ImgInterface boardImg);
    void showWithInput(KeyboardInputHandler inputHandler);
    void repaint();
    Moves.Pair mToCell(double[] posM);
} 
