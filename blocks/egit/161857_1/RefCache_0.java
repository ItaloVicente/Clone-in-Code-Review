	protected synchronized void remove(Collection<Repository> repositories) {
		branchRefs.keySet().removeAll(repositories);
		additionalRefs.keySet().removeAll(repositories);
		refsChangedListeners.keySet().removeAll(repositories);
		indexChangedListeners.keySet().removeAll(repositories);
	}

