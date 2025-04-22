		TreeWalk walk = new TreeWalk(reader);
		walk.setRecursive(true);
		walk.addTree(previousCommit.getTree());
		walk.addTree(headCommit.getTree());

		List<DiffEntry> entries = DiffEntry.scan(walk);
		if (entries.size() < 2)
