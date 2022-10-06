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
- [X] Document all methods
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

Gradle:

1. Clone to your local.
2. Build: `./gradlew build`
3. Test: `./gradlew test`
4. Run: `./gradlew run --args "infile outfile transform"`

*Note*: If you omit `--args` and everything after it, the app will return basic usage instructions.

Real Life using IntelliJ IDEA and Terminal:

1. Configure Build Artifacts output to a folder that makes sense to you for example: `./project-root/build-output`.
2. Pick your output file type: `JAR`.
3. Configure Build to point to `bitmap-transformer.app.main`.
4. Select build target `bitmap-transformer [app:build]`.
5. Review the MANIFEST.MF to ensure it points to your intended Main class.
6. Click Build and review the output. It should show `bitmap-transformer.app.main` and its output file location.
7. In the terminal, change directory to the build output folder or copy `bitmap-transfomer-jar` to a useful location.
8. In the terminal run `> java -jar bitmap-transformer-jar` and you should get helpful output from the App.

For detailed steps on the many ways to build and execute a Java application, check out [writeup on the subject on baeldung dot com](https://www.baeldung.com/java-run-jar-with-arguments)

Steps 7 and 8 can be run in a shell environment, so long as the Java SDK is in the path or environment variables.

Review 'META-INF' and '.idea/misc.xml' for this project's default build metadata.

### Args

Infile: This should point to an existing PNG file at the working directory.

Outfile: This should be the output filename of type PNG in the format 'filename.png'.

Transform: There are three:

1. bars: Adds jail bars to the image.
2. rotate: Rotates the image by 90 degrees clockwise.
3. mirror: Flip the image along the vertical axis.

## Testing Requirements

- [X] Use JUnit
- [X] Define descriptive tests
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

IntelliJ IDEA [Help Compiling Applications](https://www.jetbrains.com/help/idea/compiling-applications.html)

Build and execute [advice at baeldung dot com](https://www.baeldung.com/java-run-jar-with-arguments)

Regex help from [regex 101 dot com](https://www.regex101.com)
