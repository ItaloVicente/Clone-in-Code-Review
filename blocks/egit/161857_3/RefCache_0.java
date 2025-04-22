	protected synchronized void remove(Collection<Repository> repositories) {
		for (Repository repo : repositories) {
			ListenerHandle listener = refsChangedListeners.get(repo);
			if (listener != null) {
				listener.remove();
			}
			listener = indexChangedListeners.get(repo);
			if (listener != null) {
				listener.remove();
			}
		}
		refsChangedListeners.keySet().removeAll(repositories);
		indexChangedListeners.keySet().removeAll(repositories);
		branchRefs.keySet().removeAll(repositories);
		additionalRefs.keySet().removeAll(repositories);
	}

