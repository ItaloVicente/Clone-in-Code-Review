	public void addTree(byte[] pathPrefix
			AnyObjectId tree) throws IOException {
		CanonicalTreeParser p = createTreeParser(pathPrefix
		while (!p.eof()) {
			if (isTree(p)) {
				p = enterTree(p
				continue;
			}

			DirCacheEntry first = toEntry(stage
			beforeAdd(first);
			fastAdd(first);
			p = p.next();
			break;
		}

		while (!p.eof()) {
			if (isTree(p)) {
				p = enterTree(p
			} else {
				fastAdd(toEntry(stage
				p = p.next();
			}
