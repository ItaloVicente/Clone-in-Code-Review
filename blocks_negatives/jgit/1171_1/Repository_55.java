	/**
	 * Construct a representation of a Git repository.
	 *
	 * The work tree, object directory, alternate object directories and index
	 * file locations are deduced from the given git directory and the default
	 * rules.
	 *
	 * @param d
	 *            GIT_DIR (the location of the repository metadata). May be
	 *            null work workTree is set
	 * @param workTree
	 *            GIT_WORK_TREE (the root of the checkout). May be null for
	 *            default value.
	 * @throws IOException
	 *             the repository appears to already exist but cannot be
	 *             accessed.
	 */
	public Repository(final File d, final File workTree) throws IOException {
		this(d, workTree, null, null, null); // go figure it out
