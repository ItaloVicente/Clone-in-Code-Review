		return scan(walk
	}

	public static List<DiffEntry> scan(TreeWalk walk
			throws IOException {
		if (includeTrees && walk.isRecursive())
			throw new IllegalArgumentException(
					JGitText.get().cannotBeRecursiveWhenTreesAreIncluded);

