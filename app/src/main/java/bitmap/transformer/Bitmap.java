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

    public void getInputFile() {
        try {
            this.bufferedImage = ImageIO.read(new File(this.inFilePath));
            this.originalWidth = this.bufferedImage.getWidth();
            this.originalHeight = this.bufferedImage.getHeight();
        } catch (NullPointerException nullPointer) {
            System.out.println("Unable to read input filepath. " + nullPointer.getMessage());
        } catch (IOException inputOutput) {
            System.out.println("A problem occurred while reading input file into memory. " + inputOutput.getMessage());
        } catch (Exception exception) {
            System.out.println("A problem occurred while getting the input file path. " + exception.getMessage());
        }
    }

    public void processFile() {
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
                throw new IllegalStateException("Unexpected value: " + param);
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

    public void createOutputFile() {
        try {
//            Path path = Paths.get(this.outFilePath);
            File outputFile = new File(this.outFilePath);
            var formatName = "png"; // write() does not support bmp although imageio has a bmp plugin built in?
            ImageIO.write(this.bufferedImage, formatName, outputFile);

        } catch (InvalidPathException invalidPath) {
            System.out.println("Unable to create file at " + this.outFilePath + ". " + invalidPath.getMessage());
        } catch (FileNotFoundException fileNotFound) {
            System.out.println("Unable to find file at " + this.outFilePath + ". " + fileNotFound.getMessage());
        } catch (IOException inputOutput) {
            System.out.println("Unable to write to file " + this.outFilePath + ". " + inputOutput.getMessage());
        } catch (Exception exception) {
            System.out.println("Some other exception was thrown, message: " + exception.getMessage());
        }
        System.out.println("Wrote edits to file " + this.outFilePath);
    }
}
