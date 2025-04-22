	/**
	 * Computes the length of the index blob for a given file.
	 *
	 * @param file
	 *            on disk
	 * @param streamType
	 *            specifying CRLF translation
	 * @return the number of bytes after CRLF translations have been done.
	 * @throws IOException
	 *             if the file cannot be read
	 */
	private long getEntryContentLength(File file, EolStreamType streamType)
			throws IOException {
		if (streamType == EolStreamType.DIRECT) {
			return file.length();
		}
		long length = 0;
		try (InputStream is = EolStreamTypeUtil.wrapInputStream(
				new BufferedInputStream(new FileInputStream(file)),
				streamType)) {
			for (;;) {
				long n = is.skip(1 << 20);
				if (n <= 0) {
					break;
				}
				length += n;
