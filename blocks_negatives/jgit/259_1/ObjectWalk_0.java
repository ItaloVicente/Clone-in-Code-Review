		fromTreeWalk = false;

		if (nextSubtree != null) {
			treeWalk = treeWalk.createSubtreeIterator0(db, nextSubtree, curs);
			nextSubtree = null;
		}
