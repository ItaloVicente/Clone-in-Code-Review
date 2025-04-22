		root = db.getWorkTree();

		final DirCache dirc = db.lockDirCache();
		final DirCacheBuilder edit = dirc.builder();

		final TreeWalk walk = new TreeWalk(db);
		walk.setRecursive(true);
		walk.setFilter(paths);
		walk.addTree(new DirCacheBuildIterator(edit));

		while (walk.next()) {
			final File path = new File(root, walk.getPathString());
			final FileMode mode = walk.getFileMode(0);
			if (mode.getObjectType() == Constants.OBJ_BLOB) {
				delete(path);
			}
		}

		edit.commit();
