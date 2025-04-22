	protected TreeWalk getTreeWalk(Repository repo, RevTree revTree, String path)
			throws IOException {
		tw = new TreeWalk(repo);
		tw.reset();
		tw.addTree(revTree);
		tw.setFilter(PathFilter.create(path));

		while (tw.next() && !path.equals(tw.getPathString()))
			if (tw.isSubtree())
				tw.enterSubtree();

		return tw;
