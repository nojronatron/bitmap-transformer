package bitmap.transformer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Bitmap {
    String inFilePath;
    String outFilePath;
    private File bmpFile;

    public Bitmap(String filepath) {
        this.inFilePath = filepath;
    }

    public void getInputFile() {
        try {
            File file = new File(this.inFilePath);
            int rgbBit;

            BufferedImage bufferedImage = ImageIO.read(file);
            var imageWidth = bufferedImage.getWidth();
            var imageHeight = bufferedImage.getHeight();

            for (int row = 0; row < imageHeight; row++) {
                for (int col = 0; col < imageWidth; col++) {
                    rgbBit = bufferedImage.getRGB(col, row);
                    // TODO: process file contents
                }
            }
        } catch (NullPointerException npex) {
            System.out.println("Unable to read input filepath. " + npex.getMessage());
        } catch (IOException ioex) {
            System.out.println("A problem occurred while reading input file into memory. " + ioex.getMessage());
        }

    }

    public boolean createOutputFile(Object inputData) {
        try {
            Path path = Paths.get(this.outFilePath);
            // TODO: more work here
            return true;
        } catch (InvalidPathException ipe){
            System.out.println("Unable to create file at " + this.outFilePath + "." + ipe.getMessage());
        }

        return false;
    }
}
