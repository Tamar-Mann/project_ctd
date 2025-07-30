package it1engine.impl.model;

import it1engine.interfaces.GraphicsInterface;
import it1engine.interfaces.command.CommandInterface;
import it1engine.interfaces.ImgInterface;
import it1engine.interfaces.BoardInterface;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

//currentframe and spritefolder?!...

/**
 * Graphics class - handles sprite animation for game objects.
 */
public class Graphics implements GraphicsInterface {
    private Path spritesFolder;
    private BoardInterface board;
    private boolean loop;
    private float fps;

    private long animationStartTime;
    private int currentFrame;
    private CommandInterface currentCommand;
    private ImgInterface currentImage;

    public Graphics(Path spritesFolder, BoardInterface board, boolean loop, float fps) {
        this.spritesFolder = spritesFolder;
        this.board = board;
        this.loop = loop;
        this.fps = fps;
        this.animationStartTime = 0;
        this.currentFrame = 0;
        this.currentCommand = null;
        this.currentImage = null;
    }

    public Graphics(Path spritesFolder, BoardInterface board) {
        this(spritesFolder, board, true, 6.0f); // default loop true, 6 fps
    }

    @Override
    //
    public void reset(CommandInterface cmd) {

        if (cmd == null)
            return;
        this.animationStartTime = cmd.getTimestamp();
        this.currentCommand = cmd;
        this.currentFrame = 0;
        this.currentImage = null;
    }

    @Override
    public void update(long nowMs) {
        if (currentCommand == null)
            return;

        float frameTime = 1000.0f / fps;
        long elapsedMs = nowMs - animationStartTime;
        int totalFrames = countAvailableFrames();

        if (!loop && elapsedMs >= totalFrames * frameTime) {
            currentFrame = totalFrames - 1;
        } else {
            currentFrame = (int) ((elapsedMs / frameTime) % totalFrames);
        }

        this.currentImage = loadFrame(currentFrame);
    }

    @Override
    public GraphicsInterface copy() {
        Graphics g = new Graphics(spritesFolder, board, loop, fps);
        g.animationStartTime = this.animationStartTime;
        g.currentFrame = this.currentFrame;
        g.currentCommand = this.currentCommand;
        g.currentImage = (this.currentImage != null) ? this.currentImage.clone() : null;
        return g;
    }

    private ImgInterface loadFrame(int frameIndex) {
        String relativePath = spritesFolder.toString()
                .replace("\\", "/");

        // מצא את הקטע שמתחיל מה-resources
        int index = relativePath.indexOf("pieces/");
        if (index == -1) {
            throw new RuntimeException("Invalid sprites folder path: " + relativePath);
        }

        relativePath = relativePath.substring(index) + "/" + (frameIndex + 1) + ".png";

        return new Img().read(relativePath);
    }

    private int countAvailableFrames() {
        int count = 0;
        while (true) {
            String relativePath = spritesFolder.toString()
                    .replace("\\", "/")
                    .replaceFirst("^.*?resources/", "") + "/" + (count + 1) + ".png";

            if (getClass().getClassLoader().getResource(relativePath) == null) {
                break;
            }
            count++;
        }
        return Math.max(count, 1);
    }

    @Override
    public boolean isLooping() {
        return loop;
    }

    @Override
    public float getFps() {
        return fps;
    }

    @Override
    public Path getSpritesFolder() {
        return spritesFolder;
    }

    @Override
    public BoardInterface getBoard() {
        return board;
    }

    @Override
    public void setLooping(boolean loop) {
        this.loop = loop;
    }

    @Override
    public void setFps(float fps) {
        this.fps = fps;
    }

    public boolean isAnimating() {
        return currentCommand != null;
    }

    public void stopAnimation() {
        this.currentCommand = null;
        this.currentImage = null;
        this.currentFrame = 0;
    }

    public float getAnimationProgress() {
        if (currentCommand == null)
            return 0.0f;

        float frameTime = 1000.0f / fps;
        int totalFrames = countAvailableFrames();
        long elapsedMs = System.currentTimeMillis() - animationStartTime;
        return Math.min(1.0f, elapsedMs / (frameTime * totalFrames));
    }

    @Override
    public ImgInterface getImg() {
        File folder = spritesFolder.toFile();
        File[] files = folder.listFiles((dir, name) -> name.matches("\\d+\\.png"));
        if (files == null || files.length == 0) {
            throw new IllegalStateException("No image frames found in sprites folder: " + spritesFolder);
        }

        // ממוין לפי שם כדי לשמור על סדר
        Arrays.sort(files);

        long now = System.currentTimeMillis();
        int totalFrames = files.length;
        double frameDurationMs = 1000.0 / fps;
        int frameIndex = (int) ((now / frameDurationMs) % totalFrames);
        if (!loop && frameIndex >= totalFrames) {
            frameIndex = totalFrames - 1;
        }

        return loadFrame(frameIndex);
    }

}
