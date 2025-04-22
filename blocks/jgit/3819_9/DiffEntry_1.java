		return scan(walk
	}

	public static List<DiffEntry> scan(TreeWalk walk
			throws IOException {
		if (walk.getTreeCount() != 2)
			throw new IllegalArgumentException(
					JGitText.get().treeWalkMustHaveExactlyTwoTrees);
		if (includeTrees && walk.isRecursive())
			throw new IllegalArgumentException(
					JGitText.get().cannotBeRecursiveWhenTreesAreIncluded);

