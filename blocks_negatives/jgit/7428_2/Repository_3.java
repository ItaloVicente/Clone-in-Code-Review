		gitDir = options.getGitDir();
		fs = options.getFS();
		workTree = options.getWorkTree();
		indexFile = options.getIndexFile();
	}

	/** @return listeners observing only events on this repository. */
	public ListenerList getListenerList() {
		return myListeners;
