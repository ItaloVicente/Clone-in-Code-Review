	 * rules.
	 *
	 * @param d
	 *            GIT_DIR (the location of the repository metadata).
	 * @throws IOException
	 *             the repository appears to already exist but cannot be
	 *             accessed.
	 */
	public FileRepository(final File d) throws IOException {
		this(d, null, null, null, null); // go figure it out
	}

	/**
	 * Construct a representation of a Git repository.
