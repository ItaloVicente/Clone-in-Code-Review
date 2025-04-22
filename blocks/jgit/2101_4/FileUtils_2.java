	public static void mkdir(final File d)
			throws IOException {
		mkdir(d
	}

	public static void mkdir(final File d
			throws IOException {
		if (!d.mkdir()) {
			if (skipExisting && d.isDirectory())
				return;
			throw new IOException(MessageFormat.format(
					JGitText.get().mkDirFailed
		}
	}

	public static void mkdirs(final File d) throws IOException {
		mkdirs(d
	}

	public static void mkdirs(final File d
			throws IOException {
		if (!d.mkdirs()) {
			if (skipExisting && d.isDirectory())
				return;
			throw new IOException(MessageFormat.format(
					JGitText.get().mkDirsFailed
		}
	}
