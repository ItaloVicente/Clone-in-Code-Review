		final TreeWalk tw = new TreeWalk(db);
		tw.setRecursive(true);
		tw.reset();
		tw.addTree(c.getParent(0).getTree());
		tw.addTree(c.getTree());
		tw.setFilter(AndTreeFilter.create(pathFilter, TreeFilter.ANY_DIFF));

		List<DiffEntry> files = DiffEntry.scan(tw);
		if (pathFilter instanceof FollowFilter && isAdd(files)) {
			tw.reset();
			tw.addTree(c.getParent(0).getTree());
			tw.addTree(c.getTree());
			tw.setFilter(TreeFilter.ANY_DIFF);
			files = updateFollowFilter(detectRenames(DiffEntry.scan(tw)));

		} else if (detectRenames)
			files = detectRenames(files);

		if (showNameAndStatusOnly) {
			Diff.nameStatus(out, files);

		} else {
			diffFmt.setRepository(db);
			diffFmt.format(files);
