		TreeWalk treeWalk = new TreeWalk(db);
		treeWalk.setRecursive(false);
		treeWalk.addTree(commit.getTree());
		treeWalk.addTree(new DirCacheIterator(db.readDirCache()));
		treeWalk.addTree(new FileTreeIterator(db));
		treeWalk.setFilter(new IndexDiffFilter(1, 2));
