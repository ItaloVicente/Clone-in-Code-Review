		try (RevWalk rw = new RevWalk(repo);
				TreeWalk treeWalk = new TreeWalk(repo)) {
			if (objectId != null)
				tree = rw.parseTree(objectId);
			else
				tree = null;
