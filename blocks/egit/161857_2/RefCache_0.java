	protected synchronized void remove(Collection<Repository> repositories) {
		refsChangedListeners.keySet().removeAll(repositories);
		indexChangedListeners.keySet().removeAll(repositories);
		branchRefs.keySet().removeAll(repositories);
		additionalRefs.keySet().removeAll(repositories);
	}

