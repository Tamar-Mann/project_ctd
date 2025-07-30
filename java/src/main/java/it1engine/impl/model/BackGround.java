package it1engine.impl.model;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import it1engine.interfaces.BackGroundInterface;
import it1engine.interfaces.ImgInterface;



public class BackGround implements BackGroundInterface {
    // ─── Fields ───────────────────────────────────────────────
    private int hPix;
    private int wPix;
    private ImgInterface img;
    public BackGround(int hPix, int wPix, ImgInterface img) {
        this.hPix = hPix;
        this.wPix = wPix;
        this.img = img;
    }
    // ─── Getters and Setters ──────────────────────────────────
    public int getHPix() {
        return hPix;
    }
    public int getWPix() {
        return wPix;
    }
    public ImgInterface getImg() {
        return img;
    }
    public void setHPix(int hPix) {
        this.hPix = hPix;
    }
    public void setWPix(int wPix) {
        this.wPix = wPix;
    }
    public void setImg(ImgInterface img) {
        this.img = img;
    }
    // ─── Clone Method ─────────────────────────────────────────
    public BackGroundInterface clone() {
        return new BackGround(hPix, wPix, img);
    }
    // ─── toString Method ──────────────────────────────────────
    @Override
    public String toString() {
        return "BackGround{" +
                "hPix=" + hPix +
                ", wPix=" + wPix +
                ", img=" + img +
                '}';
    }
    // ─── Method ──────────────────────────────────────────
    public void show() {
        System.out.printf(":frame_with_picture: Showing background image with size: %dx%d\n",
                img.getBufferedImage().getWidth(), img.getBufferedImage().getHeight());
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chess Board");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JLabel label = new JLabel(new ImageIcon(this.img.getBufferedImage()));
            frame.getContentPane().add(label);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
    // ─── equals and hashCode Methods ──────────────────────────
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof BackGround))
            return false;
        BackGround that = (BackGround) o;
        return hPix == that.hPix &&
                wPix == that.wPix &&
                img.equals(that.img);
    }
}