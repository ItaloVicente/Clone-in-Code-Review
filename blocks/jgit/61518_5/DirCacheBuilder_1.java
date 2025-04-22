	public void addTree(byte[] pathPrefix
			AnyObjectId tree) throws IOException {
		pathPrefix = normalizePrefix(pathPrefix);
		RawTreeIterator p = new RawTreeIterator(reader
		while (p.hasNext()) {
			p.next();
			if (p.isTree()) {
				p = p.enter(reader);
			} else {
				DirCacheEntry e = toEntry(pathPrefix
				beforeAdd(e);
				fastAdd(e);
				break;
			}
			if (!p.hasNext()) {
				p = p.exit();
			}
		}

		while (p.hasNext()) {
			p.next();
			if (p.isTree()) {
				p = p.enter(reader);
			} else {
				fastAdd(toEntry(pathPrefix
			}
			if (!p.hasNext()) {
				p = p.exit();
			}
