		boolean needsCommit;
		try (TreeWalk treeWalk = new TreeWalk(repo)) {
			treeWalk.reset();
			treeWalk.setRecursive(true);
			treeWalk.addTree(new DirCacheIterator(dc));
			if (id == null)
				throw new NoHeadException(
						JGitText.get().cannotRebaseWithoutCurrentHead);
