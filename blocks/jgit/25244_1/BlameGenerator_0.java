		if (n.sourceCommit != null && n.recursivePath) {
			treeWalk.setFilter(AndTreeFilter.create(n.sourcePath
			treeWalk.reset(n.sourceCommit.getTree()
			if (!treeWalk.next())
				return blameEntireRegionOnParent(n
			if (isFile(treeWalk.getRawMode(1))) {
				treeWalk.getObjectId(idBuf
				return splitBlameWithParent(n
