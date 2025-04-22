	public void addTree(final byte[] pathPrefix, final int stage,
			final ObjectReader reader, final AnyObjectId tree) throws IOException {
		final TreeWalk tw = new TreeWalk(reader);
		tw.addTree(new CanonicalTreeParser(pathPrefix, reader, tree
				.toObjectId()));
		tw.setRecursive(true);
		if (tw.next()) {
			final DirCacheEntry newEntry = toEntry(stage, tw);
			beforeAdd(newEntry);
			fastAdd(newEntry);
			while (tw.next())
				fastAdd(toEntry(stage, tw));
