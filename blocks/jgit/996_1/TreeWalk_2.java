		try {
			r.setFilter(PathFilterGroup.createFromStrings(Collections
					.singleton(path)));
			r.setRecursive(r.getFilter().shouldBeRecursive());
			r.reset(trees);
			return r.next() ? r : null;
		} finally {
			r.release();
		}
