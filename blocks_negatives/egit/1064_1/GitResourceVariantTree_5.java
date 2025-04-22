	/**
	 * A map of a given resource's trail of commits.
	 */
	private Map<String, RevCommitList<RevCommit>> dates = new HashMap<String, RevCommitList<RevCommit>>();

	/**
	 * A map of a given resource to its latest blob within the branch.
	 */
	private Map<String, ObjectId> updated = new HashMap<String, ObjectId>();

	/**
	 * A map of repositories to their trees.
	 */
	private Map<Repository, Tree> trees = new HashMap<Repository, Tree>();

	private GitSynchronizeDataSet gsdData;
