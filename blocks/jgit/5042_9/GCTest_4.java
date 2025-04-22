
	private RevCommit commitChain(int depth) throws Exception {
		if (depth <= 0)
			throw new IllegalArgumentException("Chain depth must be > 0");
		CommitBuilder cb = tr.commit();
		RevCommit tip;
		do {
			--depth;
			tip = cb.add("a"
			cb = cb.child();
		} while (depth > 0);
		return tip;
	}

	private static long now() {
		return System.currentTimeMillis();
	}
