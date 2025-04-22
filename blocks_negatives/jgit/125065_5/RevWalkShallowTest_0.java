	public void testMergeCommitOneParentShallow() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		final RevCommit d = commit(b);
		final RevCommit e = commit(d);
		final RevCommit merge = commit(c, e);

		createShallowFile(e);

		rw.reset();
		markStart(merge);
		assertCommit(merge, rw.next());
		assertCommit(e, rw.next());
		assertCommit(c, rw.next());
		assertCommit(b, rw.next());
		assertCommit(a, rw.next());
