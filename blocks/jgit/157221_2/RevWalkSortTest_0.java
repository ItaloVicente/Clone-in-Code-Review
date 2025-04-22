
	@Test
	public void testSort_TOPO_OutOfOrderCommitTimes() throws Exception {
		final RevCommit a = commit();
		final RevCommit c1 = commit(a);
		final RevCommit b = commit(a);
		final RevCommit c2 = commit(c1);
		final RevCommit d = commit(b

		rw.sort(RevSort.TOPO);
		markStart(d);
		assertCommit(d
		assertCommit(c2
		assertCommit(c1
		assertCommit(b
		assertCommit(a
		assertNull(rw.next());
	}

        @Test
	public void testSort_TOPO_MultipleLinesOfHistory() throws Exception {
		final RevCommit a = commit();
		final RevCommit b1 = commit(a);
		final RevCommit c1 = commit(a);
		final RevCommit b2 = commit(a
		final RevCommit c2 = commit(b2

		rw.sort(RevSort.TOPO);
		markStart(c2);
		assertCommit(c2
		assertCommit(c1
		assertCommit(b2
		assertCommit(b1
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testSort_TOPO_REVERSE_MultipleLinesOfHistory()
			throws Exception {
		final RevCommit a = commit();
		final RevCommit b1 = commit(a);
		final RevCommit c1 = commit(a);
		final RevCommit b2 = commit(a
		final RevCommit c2 = commit(b2

		rw.sort(RevSort.TOPO);
		rw.sort(RevSort.REVERSE
		markStart(c2);
		assertCommit(a
		assertCommit(b1
		assertCommit(b2
		assertCommit(c1
		assertCommit(c2
		assertNull(rw.next());
	}
