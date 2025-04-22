		DirCacheBuilder builder = ret.builder();
		TreeWalk tw = new TreeWalk(reader);
		tw.addTree(treeId);
		tw.setRecursive(true);
		while (tw.next()) {
			DirCacheEntry e = new DirCacheEntry(tw.getRawPath());
			e.setFileMode(tw.getFileMode(0));
			e.setObjectId(tw.getObjectId(0));
			builder.add(e);
