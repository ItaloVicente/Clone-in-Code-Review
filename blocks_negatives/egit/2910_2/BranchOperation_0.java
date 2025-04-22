	 * @param refName
	 *            Name of git ref to checkout
	 */
	public BranchOperation(Repository repository, String refName) {
		this.repository = repository;
		this.refName = refName;
		this.commitId = null;
	}

	/**
	 * Construct a {@link BranchOperation} object for a commit.
	 *
	 * @param repository
	 * @param commit
