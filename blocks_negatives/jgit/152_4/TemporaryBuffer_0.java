 * Initially this output stream buffers to memory, like ByteArrayOutputStream
 * might do, but it shifts to using an on disk temporary file if the output gets
 * too large.
 * <p>
 * The content of this buffered stream may be sent to another OutputStream only
 * after this stream has been properly closed by {@link #close()}.
