	private static CanonicalTreeParser createTreeParser(byte[] pathPrefix
			ObjectReader reader
		return new CanonicalTreeParser(pathPrefix
	}

	private static boolean isTree(CanonicalTreeParser p) {
		return (p.getEntryRawMode() & TYPE_MASK) == TYPE_TREE;
	}

	private static CanonicalTreeParser enterTree(CanonicalTreeParser p
			ObjectReader reader) throws IOException {
		p = p.createSubtreeIterator(reader);
		return p.eof() ? p.next() : p;
	}

	private static DirCacheEntry toEntry(int stage
		byte[] buf = i.getEntryPathBuffer();
		int len = i.getEntryPathLength();
		byte[] path = new byte[len];
		System.arraycopy(buf
