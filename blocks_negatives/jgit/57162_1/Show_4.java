		final TreeWalk walk = new TreeWalk(db);
		walk.reset();
		walk.addTree(obj);

		while (walk.next()) {
			outw.print(walk.getPathString());
			final FileMode mode = walk.getFileMode(0);
			if (mode == FileMode.TREE)
			outw.println();
