		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

		tw.reset();
		tw.setRecursive(false);
		tw.setFilter(TreeFilter.ANY_DIFF);
		try {
			RevCommit commit = gitCommit.getRemoteCommit();
			tw.addTree(commit.getParent(0).getTree());
			int nth = tw.addTree(commit.getTree());

			while (tw.next()) {
				ResourceTraversal traversal = getTraversal(tw, workspaceRoot,
						nth);
