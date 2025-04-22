		repository = repo;
		Entry entry;
		if (ignoreNode instanceof PerDirectoryIgnoreNode)
			entry = ((PerDirectoryIgnoreNode) ignoreNode).entry;
		else
			entry = null;
		ignoreNode = new RootIgnoreNode(entry, repo);

		infoAttributeNode = new InfoAttributesNode(repo);
