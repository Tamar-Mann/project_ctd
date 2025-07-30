
package it1engine.interfaces;
public interface BackGroundInterface {
    int getHPix();
    int getWPix();
    ImgInterface getImg();
    void setHPix(int hPix);
    void setWPix(int wPix);
    void setImg(ImgInterface img);
    void show();
    BackGroundInterface clone();
}
