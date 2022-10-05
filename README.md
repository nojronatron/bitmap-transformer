# Bitmap Transformer

A command-line interface (CLI) that will take in a bitmap file (as a path on-disk), execute some color and/or raster transforms, and write the result to an output file.

## Features

Accept three parameters on the command line.

Transform an input image file, using input parameters, and write resulting transform file to the local file system.

Exception handling will ensure application continues or exits gracefully in case of bad or missing parameters, or bugs in the code that cause a failure in a transform.

Supports PNG file format only.

### Feature Requirements

- [X] Develop small, nested methods that perform specific activities (modularization)
- [X] Main should call helper methods to perform the work
- [ ] Document all methods
- [X] Contain a Bitmap Class that is instantiated when a BMP file is read
- [X] Three arguments are required to execute the Transform function
- [X] Three transforms must be available as instance methods within the Bitmap Class
- [X] CLI should return useful Error messages if used incorrectly
- [X] CLI should log a success message upon completion

## How To Use

This is a command-line application that needs to be built into an executable file before running.

### Requirements

- Path to a local bitmap file of type ".bmp"
- Gradle 7.4.2 or newer
- JVM: Java Eclipse Adoptium v17+
- OpenJDK: Temurin 17+

### Steps

1. Clone to your local.
2. Build: `./gradlew build`
3. Test: `./gradlew test`
4. Run: `./gradlew run --args "infile outfile transform"`

### Args

Infile: This should point to an existing PNG file at the working directory.

Outfile: This should be the output filename of type PNG in the format 'filename.png'.

Transform:

1. bars: Adds jail bars to the image.
2. rotate: Rotates the image by 90 degrees clockwise.
3. mirror: Flip the image along the vertical axis.

## Testing Requirements

- [X] Use JUnit
- [ ] Define descriptive tests
- [X] Ensure valid inputs are handled correctly
- [X] Ensure invalid inputs are handled correctly

## Tips From The Original Assignment Text

### Strategy

1. Gather user input (infile, outfile, transform)
2. Read input bitmap file
3. Parse bitmap data into Bitmap object (instance of Bitmap class)
4. Use data from parsed Bitmap to execute transform on data directly
5. Write the mutated image data to an output file with the specified name

### Transform Ideas

#### Color Palette

- Invert
- Randomize
- Black and White
- Darken or Lighten
- Add or Multiply a Hue
- Add or Subtract a Contrast

#### Raster Data

- Pixelate
- Add border
- Add watermark
- Rotate
- Vertical/horizontal mirror
- Vertical/horizontal stretch

## Resources

Rotate transformation method code inspired by [StackOverflow question "Rotate 90 degree to right image in java"](https://stackoverflow.com/questions/20959796/rotate-90-degree-to-right-image-in-java)

Flip transformation method code inspired by [StackOverflow question "Flip Image with Graphics2D"](https://stackoverflow.com/questions/9558981/flip-image-with-graphics2d)
