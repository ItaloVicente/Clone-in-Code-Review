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
				throw new EOFException(JGitText.get().shortReadOfBlock);
			pos += r;
			off += r;
			len -= r;
		}
	}

