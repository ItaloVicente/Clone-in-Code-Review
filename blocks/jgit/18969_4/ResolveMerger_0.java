
	protected boolean mergeTreeWalk(TreeWalk treeWalk) throws IOException {
		boolean hasWorkingTreeIterator = tw.getTreeCount() > T_FILE;
		while (treeWalk.next()) {
			if (!processEntry(
					treeWalk.getTree(T_BASE
					treeWalk.getTree(T_OURS
					treeWalk.getTree(T_THEIRS
					treeWalk.getTree(T_INDEX
					hasWorkingTreeIterator ? treeWalk.getTree(T_FILE
							WorkingTreeIterator.class) : null)) {
				cleanUp();
				return false;
			}
			if (treeWalk.isSubtree() && enterSubtree)
				treeWalk.enterSubtree();
		}
		return true;
	}
