	private CheckoutMetadata getMetadata(DirCache cache) throws IOException {
		try (TreeWalk walk = new TreeWalk(db)) {
			walk.addTree(new DirCacheIterator(cache));
			FileTreeIterator files = new FileTreeIterator(db);
			files.setDirCacheIterator(walk, 0);
			walk.addTree(files);
			walk.setFilter(AndTreeFilter.create(
					PathFilterGroup.createFromStrings(path),
					new NotIgnoredFilter(1)));
			walk.setRecursive(true);
			if (walk.next()) {
				return new CheckoutMetadata(walk.getEolStreamType(), walk
						.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE));
