	 * Reset the state of this subscriber and clear the subscribe specs of all
	 * SubscribedRepositories.
	 */
	public void reset() {
		List<RefSpec> clearSpecs = Collections.emptyList();
		for (SubscribedRepository sr : repoSubscriptions.values())
			sr.setSubscribeSpecs(clearSpecs);
		setRestartToken(null);
		setLastPackId(null);
	}

	/**
	 * Release all resources used by this Subscriber and close all
	 * SubscribedRepositories.
