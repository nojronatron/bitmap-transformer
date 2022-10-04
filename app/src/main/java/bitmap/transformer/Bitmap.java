package bitmap.transformer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.InvalidPathException;
import java.util.Locale;

public class Bitmap {
    private final String inFilePath;
    private final String outFilePath;
    private final String transformation;
    private final int imageType;
    private BufferedImage bufferedImage;
    private int originalHeight;
    private int originalWidth;

    public Bitmap(String inFilePath, String outFilePath, String transformation) {
        this.inFilePath = inFilePath;
        this.outFilePath = outFilePath;
        this.transformation = transformation;
        this.imageType = BufferedImage.TYPE_INT_RGB;
    }

    public void getInputFile() throws NullPointerException, IOException{
        this.bufferedImage = ImageIO.read(new File(this.inFilePath));
        this.originalWidth = this.bufferedImage.getWidth();
        this.originalHeight = this.bufferedImage.getHeight();
    }

    public void processFile() throws IllegalStateException {
        String param = transformation.toLowerCase(Locale.ROOT);

        switch (param) {
            case ("bars"):
                this.addBars();
                break;
            case ("rotate"):
                this.rotateRightNinety();
                break;
            case (""):
                // todo: implement a transform here
                break;
            default:
                throw new IllegalStateException("Unsupported transform " + param);
        }
    }

    private void rotateRightNinety() {

    }

    private void addBars() {
        int hInterval = this.originalHeight / 6;
        int vInterval = this.originalWidth / 6;
        int startX = Math.round((float)hInterval / 2);

        var barWidth = Math.round((float)hInterval / 12);
        Graphics2D graphics2D = (Graphics2D) this.bufferedImage.getGraphics();
        graphics2D.setStroke(new BasicStroke(barWidth));
        graphics2D.setColor(Color.DARK_GRAY);

        for (int x = startX; x < this.originalHeight; x += hInterval) {
            graphics2D.drawLine(x,0,x,this.originalHeight);
        }
    }

    public void createOutputFile() throws InvalidPathException, FileNotFoundException, IOException{
        // Path path = Paths.get(this.outFilePath);
        File outputFile = new File(this.outFilePath);
        var formatName = "png"; // write() does not support bmp although imageio has a bmp plugin built in?
        ImageIO.write(this.bufferedImage, formatName, outputFile);
    }

    public String getInFilePath() {
        return this.inFilePath;
    }
    public String getOutFilePath() {
        return this.outFilePath;
    }
    public String getTransformation() {
        return this.transformation;
    }
}
