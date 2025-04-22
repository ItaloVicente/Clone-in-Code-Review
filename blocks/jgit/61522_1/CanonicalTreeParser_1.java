	private AttributesNode findGitAttributes(ObjectReader reader)
			throws IOException {
		final int oldPrev = prevPtr;
		final int oldCurr = currPtr;
		try {
			if (!first()) {
				initAndParse(-1
			}
			int m = FileMode.REGULAR_FILE.getBits();
			while (!eof()) {
				int cmp = pathCompare(ATTRS
				if (cmp == 0) {
					return load(reader);
				} else if (cmp > 0) {
					return noAttributes();
