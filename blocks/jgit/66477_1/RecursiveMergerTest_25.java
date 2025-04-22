		AnyObjectId blobId;
		try (TreeWalk tw = new TreeWalk(r)) {
			tw.addTree(treeId);
			tw.setFilter(PathFilter.create(path));
			tw.setRecursive(true);
			if (!tw.next()) {
				return null;
			}
			blobId = tw.getObjectId(0);
		}
