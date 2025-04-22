	private static boolean matchAnyParent(final TreeWalk walk, final int myTree) {
		final int m = walk.getRawMode(myTree);
		for (int i = 0; i < myTree; i++)
			if (walk.getRawMode(i) == m && walk.idEqual(i, myTree))
				return true;
		return false;
	}

