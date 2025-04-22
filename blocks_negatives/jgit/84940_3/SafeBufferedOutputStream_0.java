 * A BufferedOutputStream that throws an error if the final flush fails on
 * close.
 * <p>
 * Java's BufferedOutputStream swallows errors that occur when the output stream
 * tries to write the final bytes to the output during close. This may result in
 * corrupted files without notice.
 * </p>
