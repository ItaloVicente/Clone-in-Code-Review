		TestRepository<?> tr = new TestRepository<>(db);
		RevWalk rw = tr.getRevWalk();
		RevTree tree = rw.parseTree(treeish);
		RevObject obj = tr.get(tree, path);
		if (obj == null) {
			return null;
