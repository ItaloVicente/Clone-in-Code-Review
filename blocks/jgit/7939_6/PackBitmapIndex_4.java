	public static PackBitmapIndex open(
			File idxFile
			throws IOException {
		final FileInputStream fd = new FileInputStream(idxFile);
		try {
			return read(fd
		} catch (IOException ioe) {
			final String path = idxFile.getAbsolutePath();
			final IOException err;
			err = new IOException(MessageFormat.format(
					JGitText.get().unreadablePackIndex
			err.initCause(ioe);
			throw err;
		} finally {
			try {
				fd.close();
			} catch (IOException err2) {
			}
		}
	}

	public static PackBitmapIndex read(
			InputStream fd
			throws IOException {
		return new PackBitmapIndexV1(fd
	}

	protected byte[] packChecksum;

