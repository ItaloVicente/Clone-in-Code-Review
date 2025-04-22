	/**
	 * Constructor for Lfs.
	 *
	 * @param root
	 *            the path to the LFS media directory. Will be
	 *            {@code "<repo>/.git/lfs"}
	 * @deprecated use {@link #Lfs(Repository)} instead.
	 */
	@Deprecated
	public Lfs(Path root) {
		this.root = root;
	}

