package bitmap.transformer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.*;
import java.nio.file.InvalidPathException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bitmap {
    private final String inFilePath;
    private final String outFilePath;
    private final String transformation;
    private final int imageType;
    private BufferedImage bufferedImage;
    private BufferedImage outputImage;
    private int originalHeight;
    private int originalWidth;
    public static final String[] AVAILABLE_TRANSFORMATIONS = {"bars", "mirror", "rotate"};

    public Bitmap(String inFilePath, String outFilePath, String transformation) {
        this.inFilePath = inFilePath;
        this.outFilePath = outFilePath;
        this.transformation = transformation.toLowerCase(Locale.ROOT);
        this.imageType = BufferedImage.TYPE_INT_RGB;
    }

    /**
     * Verifies input filepath follows expectations of this app. Does not verify whether a file exists or not.
     * @param filePath path to input or output file.
     * @return True if path appears to be valid otherwise False.
     */
    public static boolean isFormattedFilePath(String filePath) {
        // regex101.com suggestion "(\w*-?)*(\w)*\w+(\.(png|PNG))$"gm
        Pattern pattern = Pattern.compile("(\\w*-?)*(\\w)*\\w+(\\.(png|PNG))$");
        Matcher matcher = pattern.matcher(filePath);
        return matcher.matches();
    }

    /**
     * Locates input file and loads it into memory.
     * @throws IOException if Image IO cannot read the file.
     * @throws InvalidPathException if filepath cannot be read.
     */
    public void loadInputFile() throws IOException, InvalidPathException {
        if (!Bitmap.isFormattedFilePath(this.inFilePath)) {
            throw new InvalidPathException(this.inFilePath, "File Path does not appear to be valid: " + this.inFilePath);
        }

        this.bufferedImage = ImageIO.read(new File(this.inFilePath));
        this.originalWidth = this.bufferedImage.getWidth();
        this.originalHeight = this.bufferedImage.getHeight();
    }

    /**
     * Validates input transform is supported by this application.
     * @param transformation word representation of intended transformation type.
     * @return True if transformation param is valid, False if not.
     */
    public boolean isValidTransformation(String transformation) {
        var inputTransformation = transformation.toLowerCase(Locale.ROOT);

        for(String transform: Bitmap.AVAILABLE_TRANSFORMATIONS) {
            if (transform.equals(inputTransformation)){
                return true;
            };
        }

        return false;
    }

    /**
     * Determines which transformation was selected and calls the appropriate method to process the input file.
     * @throws IllegalStateException when input argument does not match one of the three processing methods.
     */
    @SuppressWarnings("EnhancedSwitchMigration")
    public void processTransformation() throws IllegalStateException {
        String param = this.getTransformation();

        switch (param) {
            case ("bars"):
                this.addBars();
                break;
            case ("rotate"):
                this.rotateRightNinety();
                break;
            case ("mirror"):
                this.mirrorImage();
                break;
            default:
                throw new IllegalStateException("Nothing was processed: Unsupported transform \"" + param + "\".");
        }
    }

    /**
     * Flips input file image along its width and stores the result in memory.
     */
    private void mirrorImage() {
        try {
            this.outputImage = new BufferedImage(originalWidth, originalHeight, this.imageType);
            AffineTransform transform = AffineTransform.getScaleInstance(-1, 1);
            transform.translate(-originalWidth, 0);
            AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            this.outputImage = operation.filter(this.bufferedImage, this.outputImage);
        } catch (NullPointerException nullPointer) {
            System.out.println("Something bad happened while mirroring the image: " + nullPointer.getMessage());
        } catch (IllegalArgumentException illegalArgument) {
            System.out.println("Source and destination images are the same. Processing halted!");
        } catch (ImagingOpException imagingOp) {
            System.out.println("Something failed while operating on the image: " + imagingOp.getMessage());
        }
    }

    /**
     * Rotates input file image by 90 degrees clockwise and stores the result in memory.
     */
    @SuppressWarnings("SuspiciousNameCombination")
    private void rotateRightNinety() {
        try {
            var newY = this.originalWidth;
            var newX = this.originalHeight;
            int halfNewY = Math.round((float) newY / 2);
            int halfNewX = Math.round((float) newX / 2);

            this.outputImage = new BufferedImage(newX, newY, this.imageType);
            Graphics2D graphics2D = this.outputImage.createGraphics();
            graphics2D.translate((newY - newX) / 2, (newY - newX) / 2);
            graphics2D.rotate(Math.PI / 2, halfNewX, halfNewY);
            graphics2D.drawRenderedImage(this.bufferedImage, null);
        } catch (Exception exception) {
            System.out.println("Something bad happened while rotating the image: " + exception.getMessage());
        }
    }

    /**
     * Adds "jail bars" to input file image and stores the result in memory.
     */
    private void addBars() {
        int hInterval = this.originalHeight / 6;
        int startX = Math.round((float)hInterval / 2);
        var barWidth = Math.round((float)hInterval / 12);

        this.outputImage = new BufferedImage(this.originalWidth, this.originalHeight, this.imageType);
        Graphics2D graphics2D = this.outputImage.createGraphics();
        graphics2D.drawRenderedImage(this.bufferedImage, null);
        graphics2D.setStroke(new BasicStroke(barWidth));
        graphics2D.setColor(Color.DARK_GRAY);

        for (int x = startX; x < this.originalHeight; x += hInterval) {
            graphics2D.drawLine(x,0,x,this.originalHeight);
        }
    }

    /**
     * Writes the contents of the stored, edited image to the filename input by the user.
     * @throws InvalidPathException if path not found on file system.
     * @throws IOException if unable to write stored data to output file.
     */
    public void writeOutputFile() throws InvalidPathException, IOException {
        File outputFile = new File(this.outFilePath);
        var formatName = "png"; // write() does not support bmp although imageio has a bmp plugin built in?
        ImageIO.write(this.outputImage, formatName, outputFile);
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

    public int getImageType() {
        return imageType;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public BufferedImage getOutputImage() {
        return outputImage;
    }

    public int getOriginalHeight() {
        return originalHeight;
    }

    public int getOriginalWidth() {
        return originalWidth;
    }

    public String[] getAvailableTransformations() {
        return AVAILABLE_TRANSFORMATIONS;
    }
}
