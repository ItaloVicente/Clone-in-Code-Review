			newTree = new DirCacheIterator(db.readDirCache());
		} else if (oldTree == null) {
			oldTree = new DirCacheIterator(db.readDirCache());
			newTree = new FileTreeIterator(db);
		} else if (newTree == null) {
			newTree = new FileTreeIterator(db);
		}
