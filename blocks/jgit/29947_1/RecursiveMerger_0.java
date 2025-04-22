		DirCacheBuilder aBuilder = ret.builder();
		TreeWalk atw = new TreeWalk(db);
		atw.addTree(treeId);
		atw.setRecursive(true);
		while (atw.next()) {
			DirCacheEntry e = new DirCacheEntry(atw.getRawPath());
			e.setFileMode(atw.getFileMode(0));
			e.setObjectId(atw.getObjectId(0));
			aBuilder.add(e);
