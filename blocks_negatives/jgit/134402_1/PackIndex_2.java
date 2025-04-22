	public static PackIndex open(File idxFile) throws IOException {
		try (SilentFileInputStream fd = new SilentFileInputStream(
				idxFile)) {
				return read(fd);
		} catch (IOException ioe) {
			throw new IOException(
					MessageFormat.format(JGitText.get().unreadablePackIndex,
							idxFile.getAbsolutePath()),
					ioe);
		}
