	public static TreeFilter createFromStrings(final String... paths) {
		if (paths.length == 0)
			throw new IllegalArgumentException(
					JGitText.get().atLeastOnePathIsRequired);
		final int length = paths.length;
		final PathFilter[] p = new PathFilter[length];
		for (int i = 0; i < length; i++)
			p[i] = PathFilter.create(paths[i]);
		return create(p);
	}

