	void iterateOverTreeWalk(final Git git
			final BiConsumer<String
		if (headId != null) {
			try (final TreeWalk treeWalk = new TreeWalk(git.getRepository())) {
				final int hIdx = treeWalk.addTree(new RevWalk(git.getRepository()).parseTree(headId));
				treeWalk.setRecursive(true);
