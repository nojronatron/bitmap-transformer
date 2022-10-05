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

    public Bitmap(String inFilePath, String outFilePath, String transformation) {
        this.inFilePath = inFilePath;
        this.outFilePath = outFilePath;
        this.transformation = transformation;
        this.imageType = BufferedImage.TYPE_INT_RGB;
    }

    public void getInputFile() throws NullPointerException, IOException, InvalidPathException {
        // regex101.com suggestion "[a-zA-Z0-9/]*\w(?:\.png)$"gm
        Pattern pattern = Pattern.compile("[a-zA-Z\\d]*\\w(\\.png)$");
        Matcher matcher = pattern.matcher(this.outFilePath);

        if (!matcher.matches()) {
            throw new InvalidPathException(this.outFilePath, "Out File Path does not appear to be valid: " + this.outFilePath);
        }

        this.bufferedImage = ImageIO.read(new File(this.inFilePath));
        this.originalWidth = this.bufferedImage.getWidth();
        this.originalHeight = this.bufferedImage.getHeight();
    }

    @SuppressWarnings("EnhancedSwitchMigration")
    public void processFile() throws IllegalStateException {
        String param = transformation.toLowerCase(Locale.ROOT);

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

    public void createOutputFile() throws InvalidPathException, IOException {
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
}
