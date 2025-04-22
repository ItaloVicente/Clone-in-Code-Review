		if (n.sourceCommit != null && n.recursivePath) {
			treeWalk.setFilter(AndTreeFilter.create(n.sourcePath, ID_DIFF));
			treeWalk.reset(n.sourceCommit.getTree(), parent.getTree());
			if (!treeWalk.next())
				return blameEntireRegionOnParent(n, parent);
			if (isFile(treeWalk.getRawMode(1))) {
				treeWalk.getObjectId(idBuf, 1);
				return splitBlameWithParent(n, parent);
			}
		} else if (find(parent, n.sourcePath)) {
