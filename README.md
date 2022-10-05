# Bitmap Transformer

A command-line interface (CLI) that will take in a bitmap file (as a path on-disk), execute some color and/or raster transforms, and write the result to an output file.

## Features

### Feature Requirements

- [ ] Develop small, nested methods that perform specific activities (modularization)
- [X] Main should call helper methods to perform the work
- [ ] Document all methods
- [X] Contain a Bitmap Class that is instantiated when a BMP file is read
- [X] Three arguments are required to execute the Transform function
- [ ] Three transforms must be available as instance methods within the Bitmap Class
- [X] CLI should return useful Error messages if used incorrectly
- [ ] CLI should log a success message upon completion

## How To Use

### Requirements

- Path to a local bitmap file of type ".bmp"
- Gradle 7.4.2 or newer
- JVM: Java Eclipse Adoptium v17+
- OpenJDK: Temurin 17+

### Steps

1. Clone to your local.
2. Build: `./gradlew build`
3. Test: `./gradlew test`
4. Run: `./gradlew run --args "input output transform"`

## Testing Requirements

- [X] Use JUnit
- [ ] Define descriptive tests
- [ ] Ensure valid inputs are handled correctly
- [ ] Ensure invalid inputs are handled correctly

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