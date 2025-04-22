	public static void mkdir(final File d)
			throws IOException {
		mkdir(d
	}

	public static void mkdir(final File d
			throws IOException {
		if (skipExisting && d.exists() && d.isDirectory())
			return;

		if (!d.mkdir())
			throw new IOException(MessageFormat.format(
					JGitText.get().mkDirFailed
	}

	public static void mkdirs(final File d) throws IOException {
		mkdirs(d
	}

	public static void mkdirs(final File d
			throws IOException {
		if (skipExisting && d.exists() && d.isDirectory())
			return;

		if (!d.mkdirs())
			throw new IOException(MessageFormat.format(
					JGitText.get().mkDirsFailed
	}
