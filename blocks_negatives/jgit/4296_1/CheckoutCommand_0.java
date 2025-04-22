			TreeWalk treeWalk = new TreeWalk(revWalk.getObjectReader());
			treeWalk.setRecursive(true);
			treeWalk.addTree(new DirCacheIterator(dc));
			treeWalk.setFilter(PathFilterGroup.createFromStrings(paths));
			List<String> files = new LinkedList<String>();
			while (treeWalk.next())
				files.add(treeWalk.getPathString());

			if (startCommit != null || startPoint != null) {
				DirCacheEditor editor = dc.editor();
				TreeWalk startWalk = new TreeWalk(revWalk.getObjectReader());
				startWalk.setRecursive(true);
				startWalk.setFilter(treeWalk.getFilter());
