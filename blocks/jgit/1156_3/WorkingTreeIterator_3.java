	protected void initRootIterator(Repository repo) {
		Entry entry;
		if (ignoreNode instanceof PerDirectoryIgnoreNode)
			entry = ((PerDirectoryIgnoreNode) ignoreNode).entry;
		else
			entry = null;
		ignoreNode = new RootIgnoreNode(entry
	}

