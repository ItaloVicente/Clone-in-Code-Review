	GitModelWorkingTree(GitModelObject parent) throws IOException {
		this(parent, null);
	}

	/**
	 * @param parent
	 *            parent of working tree instance
	 * @param pathFilter synchronize configuration
	 * @throws IOException
	 */
	public GitModelWorkingTree(GitModelObject parent, TreeFilter pathFilter)
			throws IOException {
		super(parent, null, pathFilter, new FileModelFactory() {
