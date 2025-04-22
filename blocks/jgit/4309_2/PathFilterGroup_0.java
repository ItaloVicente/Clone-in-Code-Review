	public static TreeFilter createFromStrings(final String... paths) {
		if (paths.length == 0)
			throw new IllegalArgumentException(
					JGitText.get().atLeastOnePathIsRequired);
		final PathFilter[] p = new PathFilter[paths.length];
		for (int i = 0; i < paths.length; i++)
			p[i] = PathFilter.create(paths[i]);
		return create(p);
	}

