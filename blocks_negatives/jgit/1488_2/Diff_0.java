
	private List<DiffEntry> scan() throws IOException {
		final TreeWalk walk = new TreeWalk(db);
		walk.reset();
		walk.setRecursive(true);
		for (final AbstractTreeIterator i : trees)
			walk.addTree(i);
		walk.setFilter(AndTreeFilter.create(TreeFilter.ANY_DIFF, pathFilter));

		List<DiffEntry> files = DiffEntry.scan(walk);
		if (detectRenames) {
			RenameDetector rd = new RenameDetector(db);
			if (renameLimit != null)
				rd.setRenameLimit(renameLimit.intValue());
			rd.addAll(files);
			files = rd.compute(new TextProgressMonitor());
		}
		return files;
	}
