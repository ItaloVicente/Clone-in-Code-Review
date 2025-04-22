			df.setRepository(db);
			df.setPathFilter(PathFilter.create("folder"));
			DirCacheIterator oldTree = new DirCacheIterator(db.readDirCache());
			FileTreeIterator newTree = new FileTreeIterator(db);
			df.format(oldTree
			df.flush();
