	/**
	 * Set the $prefix directory C Git uses.
	 *
	 * @param path
	 *            the directory. Null if C Git is not installed.
	 * @return {@code this}
	 */
	public FS setGitPrefix(File path) {
		gitPrefix = new Holder<File>(path);
		return this;
	}

