		try (TreeWalk treeWalk = new TreeWalk(repository)) {
			ContainerTreeIterator tree = new ContainerTreeIterator(repository,
					project.getProject());
			int treeIndex = treeWalk.addTree(tree);
			treeWalk.setRecursive(true);
			List<Entry> entries = new ArrayList<Entry>();
			while (treeWalk.next()) {
				AbstractTreeIterator it = treeWalk.getTree(treeIndex,
						AbstractTreeIterator.class);
				Entry entry = new Entry(treeWalk.getPathString(),
						it.getClass());
				entries.add(entry);
			}
			return entries;
