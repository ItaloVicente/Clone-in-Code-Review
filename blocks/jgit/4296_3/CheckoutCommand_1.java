			DirCacheEditor editor = dc.editor();
			TreeWalk startWalk = new TreeWalk(revWalk.getObjectReader());
			startWalk.setRecursive(true);
			startWalk.setFilter(PathFilterGroup.createFromStrings(paths));
			boolean checkoutIndex = startCommit == null && startPoint == null;
			if (!checkoutIndex)
