
		addFirst(stage
		p = p.next();

		while (!p.eof()) {
			if (FileMode.TREE.equals(p.getEntryRawMode())) {
				p = p.createSubtreeIterator(reader);
			} else {
				fastAdd(toEntry(stage
				p = p.next();
			}
		}
	}

	private static CanonicalTreeParser readTree(byte[] pathPrefix
			ObjectReader reader
		return new CanonicalTreeParser(pathPrefix
	}

	private void addFirst(int stage
		DirCacheEntry first = toEntry(stage
		beforeAdd(first);
		fastAdd(first);
