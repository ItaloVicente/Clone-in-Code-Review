		if (pathFilter instanceof FollowFilter && isAdd(files)) {
			tw.reset();
			tw.addTree(c.getParent(0).getTree());
			tw.addTree(c.getTree());
			tw.setFilter(TreeFilter.ANY_DIFF);
			files = updateFollowFilter(detectRenames(DiffEntry.scan(tw)));

		} else if (detectRenames)
			files = detectRenames(files);
