
	protected boolean mergeTreeWalk(TreeWalk treeWalk) throws IOException {
		while (treeWalk.next()) {
			if (!processEntry(
					treeWalk.getTree(T_BASE
					treeWalk.getTree(T_OURS
					treeWalk.getTree(T_THEIRS
					treeWalk.getTree(T_INDEX
					(tw.getTreeCount() >= T_FILE) ? null : treeWalk.getTree(
							T_FILE
				cleanUp();
				return false;
			}
			if (treeWalk.isSubtree() && enterSubtree)
				treeWalk.enterSubtree();
		}
		return true;
	}
