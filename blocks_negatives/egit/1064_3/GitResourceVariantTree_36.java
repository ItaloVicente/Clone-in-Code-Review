	/**
	 * A map of a given resource to its latest blob within the branch.
	 */
	private Map<String, ObjectId> updated = new HashMap<String, ObjectId>();

	/**
	 * A map of repositories to their trees.
	 */
	private Map<Repository, Tree> trees = new HashMap<Repository, Tree>();

	private GitSynchronizeDataSet gsdData;

	private final ResourceVariantByteStore store;

	GitResourceVariantTree(GitSynchronizeDataSet data,
			ResourceVariantByteStore store) {
		this.store = store;
		this.gsdData = data;
