	@SuppressWarnings({ "unchecked", "resource" })
	public R build() throws IOException {
		R repo = (R) new FileRepository(setup());
		if (isMustExist() && !repo.getObjectDatabase().exists())
			throw new RepositoryNotFoundException(getGitDir());
		return repo;
	}

	/**
	 * Require either {@code gitDir} or {@code workTree} to be set.
	 */
	protected void requireGitDirOrWorkTree() {
		if (getGitDir() == null && getWorkTree() == null)
			throw new IllegalArgumentException(
					JGitText.get().eitherGitDirOrWorkTreeRequired);
	}

	/**
	 * Perform standard gitDir initialization.
	 *
	 * @throws java.io.IOException
	 *             the repository could not be accessed
	 */
	protected void setupGitDir() throws IOException {
		if (getGitDir() == null && getWorkTree() != null) {
			File dotGit = new File(getWorkTree(), DOT_GIT);
			if (!dotGit.isFile())
				setGitDir(dotGit);
			else
				setGitDir(getSymRef(getWorkTree(), dotGit, safeFS()));
		}
	}

	/**
	 * Perform standard work-tree initialization.
	 * <p>
	 * This is a method typically invoked inside of {@link #setup()}, near the
	 * end after the repository has been identified and its configuration is
	 * available for inspection.
	 *
	 * @throws java.io.IOException
	 *             the repository configuration could not be read.
	 */
	protected void setupWorkTree() throws IOException {
		if (getFS() == null)
			setFS(FS.DETECTED);

		if (!isBare() && getWorkTree() == null)
			setWorkTree(guessWorkTreeOrFail());

		if (!isBare()) {
			if (getGitDir() == null)
				setGitDir(getWorkTree().getParentFile());
			if (getIndexFile() == null)
				setIndexFile(new File(getGitDir(), "index")); //$NON-NLS-1$
		}
	}

	/**
	 * Configure the internal implementation details of the repository.
	 *
	 * @throws java.io.IOException
	 *             the repository could not be accessed
	 */
	protected void setupInternals() throws IOException {
		if (getObjectDirectory() == null && getGitDir() != null)
			setObjectDirectory(safeFS().resolve(getGitDir(), "objects")); //$NON-NLS-1$
	}
