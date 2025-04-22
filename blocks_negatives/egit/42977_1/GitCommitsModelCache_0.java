		final TreeWalk tw = new TreeWalk(repo);
		int commitIndex = addTree(tw, commit);
		int parentCommitIndex = addTree(tw, parentCommit);

		tw.setRecursive(true);
		if (pathFilter == null)
			tw.setFilter(ANY_DIFF);
		else
			tw.setFilter(AndTreeFilter.create(ANY_DIFF, pathFilter));

