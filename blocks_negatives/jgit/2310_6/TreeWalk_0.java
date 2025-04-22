		final TreeWalk r = new TreeWalk(reader);
		r.setFilter(PathFilterGroup.createFromStrings(Collections
				.singleton(path)));
		r.setRecursive(r.getFilter().shouldBeRecursive());
		r.reset(trees);
		return r.next() ? r : null;
