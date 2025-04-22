
		HashSet<FileTreeIterator> toAdd = new HashSet<FileTreeIterator>();
		while (tw.next()) {
			FileTreeIterator t = tw.getTree(0, FileTreeIterator.class);
			if (t.hasGitIgnore()) {
				toAdd.add(t);
			}
		}
		for (FileTreeIterator t : toAdd)
			addNodeFromTree(t);
