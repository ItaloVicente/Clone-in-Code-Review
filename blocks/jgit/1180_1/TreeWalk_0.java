	public static TreeWalk forPath(final ObjectReader reader
			final AnyObjectId... trees) throws MissingObjectException
			IncorrectObjectTypeException
		final TreeWalk r = new TreeWalk(reader);
		r.setFilter(PathFilterGroup.createFromStrings(Collections
				.singleton(path)));
		r.setRecursive(r.getFilter().shouldBeRecursive());
		r.reset(trees);
		return r.next() ? r : null;
	}

