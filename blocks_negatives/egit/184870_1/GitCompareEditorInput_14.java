		boolean useIndex = compareVersion.equals(CompareTreeView.INDEX_VERSION);
		boolean checkIgnored = false;

		IDiffContainer result = new DiffNode(Differencer.CONFLICTING);

		try (TreeWalk tw = new TreeWalk(repository)) {
