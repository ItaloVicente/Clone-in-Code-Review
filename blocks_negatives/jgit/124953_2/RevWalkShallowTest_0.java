		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		final RevCommit d = commit(c);

		createShallowFile(b);

		rw.reset();
		markStart(d);
		assertCommit(d, rw.next());
		assertCommit(c, rw.next());
		assertCommit(b, rw.next());
