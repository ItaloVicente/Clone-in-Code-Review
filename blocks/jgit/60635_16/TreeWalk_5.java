		return forPath(null
	}

	public static TreeWalk forPath(final @Nullable Repository repo
			final ObjectReader reader
			final AnyObjectId... trees)
					throws MissingObjectException
					CorruptObjectException
		TreeWalk tw = new TreeWalk(repo
