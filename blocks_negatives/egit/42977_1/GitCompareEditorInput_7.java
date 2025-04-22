		int baseTreeIndex;
		if (baseCommit == null) {
			checkIgnored = true;
			baseTreeIndex = tw.addTree(new AdaptableFileTreeIterator(
					repository, ResourcesPlugin.getWorkspace().getRoot()));
		} else
			baseTreeIndex = tw.addTree(new CanonicalTreeParser(null, repository
					.newObjectReader(), baseCommit.getTree()));
		int compareTreeIndex;
		if (!useIndex)
			compareTreeIndex = tw.addTree(new CanonicalTreeParser(null,
					repository.newObjectReader(), compareCommit.getTree()));
		else
			compareTreeIndex = tw.addTree(new DirCacheIterator(repository
					.readDirCache()));
