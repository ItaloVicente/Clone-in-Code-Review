	protected RevCommit commitChain(int depth
		if (depth <= 0) {
			throw new IllegalArgumentException("Chain depth must be > 0");
		}
		if (width <= 0) {
			throw new IllegalArgumentException("Number of files per commit must be > 0");
		}
		CommitBuilder cb = tr.commit();
		RevCommit tip = null;
		do {
			--depth;
			for (int i=0; i < width; i++) {
				String id = depth + "-" + i;
				cb.add("a" + id
			}
			tip = cb.create();
			cb = cb.child();
		} while (depth > 0);
		return tip;
	}

