		while (!p.eof()) {
			if (isTree(p)) {
				p = enterTree(p
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

	private static boolean isTree(CanonicalTreeParser p) {
		return (p.getEntryRawMode() & TYPE_MASK) == TYPE_TREE;
	}

	private static CanonicalTreeParser enterTree(CanonicalTreeParser p
			ObjectReader reader) throws IOException {
		p = p.createSubtreeIterator(reader);
		return p.eof() ? p.next() : p;
