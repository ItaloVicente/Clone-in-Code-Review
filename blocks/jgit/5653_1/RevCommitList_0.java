	public void fillTo(final RevCommit commitToLoad)
			throws MissingObjectException
			IOException {
		if (walker == null || commitToLoad == null)
			return;

		RevCommit c;
		do {
			c = walker.next();
			if (c == null) {
				walker = null;
				return;
			}
			enter(size++
			add((E) c);
		} while (!c.equals(commitToLoad));
	}

