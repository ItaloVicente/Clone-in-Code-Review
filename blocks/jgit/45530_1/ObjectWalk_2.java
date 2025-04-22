		objectModeCache.addRoot(tree);
		markTreeUninterestingRec(tree);
	}

	private void markTreeUninterestingRec(final RevTree tree)
			throws MissingObjectException
			IOException {
