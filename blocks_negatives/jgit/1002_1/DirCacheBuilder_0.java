			final Repository db, final AnyObjectId tree) throws IOException {
		final ObjectReader reader = db.newObjectReader();
		try {
			final TreeWalk tw = new TreeWalk(reader);
			tw.reset();
			tw.addTree(new CanonicalTreeParser(pathPrefix, reader, tree
					.toObjectId()));
			tw.setRecursive(true);
			if (tw.next()) {
				final DirCacheEntry newEntry = toEntry(stage, tw);
				beforeAdd(newEntry);
				fastAdd(newEntry);
				while (tw.next())
					fastAdd(toEntry(stage, tw));
			}
		} finally {
			reader.release();
