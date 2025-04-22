			final ObjectReader reader
		final TreeWalk tw = new TreeWalk(reader);
		tw.reset();
		tw.addTree(new CanonicalTreeParser(pathPrefix
				.toObjectId()));
		tw.setRecursive(true);
		if (tw.next()) {
			final DirCacheEntry newEntry = toEntry(stage
			beforeAdd(newEntry);
			fastAdd(newEntry);
			while (tw.next())
				fastAdd(toEntry(stage
