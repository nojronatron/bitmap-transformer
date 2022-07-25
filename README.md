# Bitmap Transformer

A command-line interface (CLI) that will take in a bitmap file (as a path on-disk), execute some color and/or raster transforms, and write the result to an output file.

## Features

### Feature Requirements

- [ ] Develop small, nested methods that perform specific activities (modularization)
- [ ] Main should call helper methods to perform the work
- [ ] Document all methods
- [ ] Contain a Bitmap Class that is instantiated when a BMP file is read
- [ ] Three arguments are required to execute the Transform function
- [ ] Three transforms must be available as instance methods within the Bitmap Class
- [ ] CLI should return useful Error messages if used incorrectly
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
3. Run: `./gradlew run -args "input output transform"`

## Testing

- [ ] Use JUnit
- [ ] Define descriptive tests
- [ ] Ensure valid inputs are handled correctly
- [ ] Ensure invalid inputs are handled correctly
