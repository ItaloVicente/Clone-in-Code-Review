	public static void mkdir(final File d) throws IOException {
		if (!d.mkdir())
			throw new IOException(MessageFormat.format(
					JGitText.get().mkDirFailed
	}

	public static void mkdirs(final File d) throws IOException {
		if (!d.mkdirs())
			throw new IOException(MessageFormat.format(
					JGitText.get().mkDirsFailed
	}
