	private CanonicalTreeParser enter(RevObject tree) throws IOException {
		CanonicalTreeParser p = treeWalk.createSubtreeIterator0(db
		if (p.eof()) {
			return treeWalk.next();
		}
		return p;
	}

