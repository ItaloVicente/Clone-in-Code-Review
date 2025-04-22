	@Override
	protected TreeWalk getTreeWalk(Repository repo, RevTree revTree,
			String path) throws IOException {
		TreeWalk tw = new TreeWalk(repo);
		tw.reset();
		tw.addTree(revTree);
		tw.setRecursive(true);
		tw.setFilter(PathFilter.create(path));

		return tw.next() ? tw : null;
	}

