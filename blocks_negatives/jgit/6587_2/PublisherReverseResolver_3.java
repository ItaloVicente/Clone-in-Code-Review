	 * @return the repository with this key, or null.
	 */
	public SubscribedRepository getRepository(String r) {
		return repoSubscriptions.get(r);
	}

	/**
	 * @return the set of all repository names this subscriber will connect to.
