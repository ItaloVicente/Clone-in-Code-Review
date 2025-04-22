		df = new PatchIdDiffFormatter();
		df.setRepository(db);
		df.setPathFilter(PathFilter.create("folder"));
		oldTree = new DirCacheIterator(db.readDirCache());
		newTree = new FileTreeIterator(db);
		df.format(oldTree, newTree);
		df.flush();
