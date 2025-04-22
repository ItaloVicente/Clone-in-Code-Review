		final TreeWalk tw = new TreeWalk(db);
		tw.setFilter(PathSuffixFilter.create(suffixFilter));
		tw.setRecursive(recursiveWalk);
		tw.addTree(treeId);

		List<String> paths = new ArrayList<String>();
		while (tw.next())
			paths.add(tw.getPathString());
		return paths;
