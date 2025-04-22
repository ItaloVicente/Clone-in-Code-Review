
	/**
	 * Create a temporary directory.
	 *
	 * @param prefix
	 * @param suffix
	 * @param dir
	 *            The parent dir, can be null to use system default temp dir.
	 * @return the temp dir created.
	 * @throws IOException
	 * @since 3.4
	 */
	public static File createTempDir(String prefix, String suffix, File dir)
			throws IOException {
		for (int i = 0; i < RETRIES; i++) {
			File tmp = File.createTempFile(prefix, suffix, dir);
			if (!tmp.delete())
				continue;
			if (!tmp.mkdir())
				continue;
			return tmp;
		}
		throw new IOException(JGitText.get().cannotCreateTempDir);
	}
