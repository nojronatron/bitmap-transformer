package bitmap.transformer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Bitmap {
    private final String inFilePath;
    private final String outFilePath;
    private final String transformation;
    private File original;
    private BufferedImage edited;
    private int originalHeight;
    private int originalWidth;

    public Bitmap(String inFilePath, String outFilePath, String transformation) {
        this.inFilePath = inFilePath;
        this.outFilePath = outFilePath;
        this.transformation = transformation;
    }

    public void getInputFile() {
        try {
            String userDir = System.getProperty("user.dir");
            Path path = Paths.get(this.inFilePath);

            this.original = new File(this.inFilePath);

        } catch (NullPointerException nullPointer) {
            System.out.println("Unable to read input filepath. " + nullPointer.getMessage());
        }
    }

    public void processFile() {
        try {
//            int rgbBit;
            BufferedImage bufferedImage = ImageIO.read(this.original);
            var imageWidth = bufferedImage.getWidth();
            this.originalWidth = imageWidth;
            var imageHeight = bufferedImage.getHeight();
            this.originalHeight = imageHeight;
            var writableRaster = bufferedImage.getRaster();

//            for (int row = 0; row < imageHeight; row++) {
//                for (int col = 0; col < imageWidth; col++) {
//                    rgbBit = bufferedImage.getRGB(col, row);
//                    // TODO: process file contents
//                }
//            }

            int hInterval = imageHeight / 6;
            int vInterval = imageWidth / 6;
            var spotColor = bufferedImage.getRGB(130, 130);
//            System.out.println("spotColor: " + spotColor);

            for (int x = 0; x < imageHeight; x += hInterval) {
                for (int y = 0; y < imageWidth; y += vInterval) {
                    bufferedImage.setRGB(x, y, -16711680);
                }
            }

            this.edited = bufferedImage;
        } catch (IOException inputOutput) {
            System.out.println("A problem occurred while reading input file into memory. " + inputOutput.getMessage());
        }
    }

    public void createOutputFile() {
        try {
//            Path path = Paths.get(this.outFilePath);
            File outputFile = new File(this.outFilePath);
            ImageIO.write(this.edited, "bmp", outputFile);

        } catch (InvalidPathException invalidPath) {
            System.out.println("Unable to create file at " + this.outFilePath + ". " + invalidPath.getMessage());
        } catch (FileNotFoundException fileNotFound) {
            System.out.println("Unable to find file at " + this.outFilePath + ". " + fileNotFound.getMessage());
        } catch (IOException inputOutput) {
            System.out.println("Unable to write to file " + this.outFilePath + ". " + inputOutput.getMessage());
        }
    }
}
