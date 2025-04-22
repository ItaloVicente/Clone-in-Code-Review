		return repo;
	}

	/**
	 * Set repository and tree walk
	 *
	 * @param repository
	 * @param walk
	 */
	public void setTreeWalk(Repository repository, TreeWalk walk) {
		db = repository;
		walker = walk;
