	/**
	 * Read an entire local file into memory as a byte array.
	 *
	 * @param path
	 *            location of the file to read.
	 * @return complete contents of the requested local file.
	 * @throws FileNotFoundException
	 *             the file does not exist.
	 * @throws IOException
	 *             the file exists, but its contents cannot be read.
	 */
	public static final byte[] readFully(final File path)
			throws FileNotFoundException, IOException {
		return readFully(path, Integer.MAX_VALUE);
	}

	/**
	 * Read an entire local file into memory as a byte array.
	 *
	 * @param path
	 *            location of the file to read.
	 * @param max
	 *            maximum number of bytes to read, if the file is larger than
	 *            this limit an IOException is thrown.
	 * @return complete contents of the requested local file.
	 * @throws FileNotFoundException
	 *             the file does not exist.
	 * @throws IOException
	 *             the file exists, but its contents cannot be read.
	 */
	public static final byte[] readFully(final File path, final int max)
			throws FileNotFoundException, IOException {
		final FileInputStream in = new FileInputStream(path);
		try {
			final long sz = in.getChannel().size();
			if (sz > max)
				throw new IOException("File is too large: " + path);
			final byte[] buf = new byte[(int) sz];
			readFully(in, buf, 0, buf.length);
			return buf;
		} finally {
			try {
				in.close();
			} catch (IOException ignored) {
			}
		}
	}

	/**
	 * Read the entire byte array into memory, or throw an exception.
	 *
	 * @param fd
	 *            input stream to read the data from.
	 * @param dst
	 *            buffer that must be fully populated, [off, off+len).
	 * @param off
	 *            position within the buffer to start writing to.
	 * @param len
	 *            number of bytes that must be read.
	 * @throws EOFException
	 *             the stream ended before dst was fully populated.
	 * @throws IOException
	 *             there was an error reading from the stream.
	 */
	public static void readFully(final InputStream fd, final byte[] dst,
			int off, int len) throws IOException {
		while (len > 0) {
			final int r = fd.read(dst, off, len);
			if (r <= 0)
				throw new EOFException("Short read of block.");
			off += r;
			len -= r;
		}
	}

	/**
	 * Read the entire byte array into memory, or throw an exception.
	 *
	 * @param fd
	 *            file to read the data from.
	 * @param pos
	 *            position to read from the file at.
	 * @param dst
	 *            buffer that must be fully populated, [off, off+len).
	 * @param off
	 *            position within the buffer to start writing to.
	 * @param len
	 *            number of bytes that must be read.
	 * @throws EOFException
	 *             the stream ended before dst was fully populated.
	 * @throws IOException
	 *             there was an error reading from the stream.
	 */
	public static void readFully(final FileChannel fd, long pos,
			final byte[] dst, int off, int len) throws IOException {
		while (len > 0) {
			final int r = fd.read(ByteBuffer.wrap(dst, off, len), pos);
			if (r <= 0)
				throw new EOFException("Short read of block.");
			pos += r;
			off += r;
			len -= r;
		}
	}

	/**
	 * Skip an entire region of an input stream.
	 * <p>
	 * The input stream's position is moved forward by the number of requested
	 * bytes, discarding them from the input. This method does not return until
	 * the exact number of bytes requested has been skipped.
	 *
	 * @param fd
	 *            the stream to skip bytes from.
	 * @param toSkip
	 *            total number of bytes to be discarded. Must be >= 0.
	 * @throws EOFException
	 *             the stream ended before the requested number of bytes were
	 *             skipped.
	 * @throws IOException
	 *             there was an error reading from the stream.
	 */
	public static void skipFully(final InputStream fd, long toSkip)
			throws IOException {
		while (toSkip > 0) {
			final long r = fd.skip(toSkip);
			if (r <= 0)
				throw new EOFException("Short skip of block");
			toSkip -= r;
		}
	}

