	public void addTree(byte[] pathPrefix
			AnyObjectId tree) throws IOException {
		CanonicalTreeParser p = readTree(pathPrefix
		if (p.eof()) {
			return;
		}

		addFirst(stage
		p = p.next();

		while (!p.eof()) {
			if (p.getEntryRawMode() == TYPE_TREE) {
				p = p.createSubtreeIterator(reader);
			} else {
				fastAdd(toEntry(stage
				p = p.next();
			}
