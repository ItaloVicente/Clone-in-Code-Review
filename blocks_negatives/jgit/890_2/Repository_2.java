	private File workDir;

	private File indexFile;

	/**
	 * Construct a representation of a Git repository.
	 *
	 * The work tree, object directory, alternate object directories and index
	 * file locations are deduced from the given git directory and the default
	 * rules.
	 *
	 * @param d
	 *            GIT_DIR (the location of the repository metadata).
	 * @throws IOException
	 *             the repository appears to already exist but cannot be
	 *             accessed.
	 */
	public Repository(final File d) throws IOException {
		this(d, null, null, null, null); // go figure it out
