		try (TreeWalk atw = new TreeWalk(reader)) {
			atw.addTree(treeId);
			atw.setRecursive(true);
			while (atw.next()) {
				DirCacheEntry e = new DirCacheEntry(atw.getRawPath());
				e.setFileMode(atw.getFileMode(0));
				e.setObjectId(atw.getObjectId(0));
				aBuilder.add(e);
			}
