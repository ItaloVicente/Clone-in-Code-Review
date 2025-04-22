
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
		final RevCommit a1 = commit();
		final RevCommit b1 = commit(a1);
		final RevCommit a2 = commit(a1
		final RevCommit b2 = commit(b1);
		final RevCommit b3 = commit(b1);
		final RevCommit a3 = commit(a2
		final RevCommit a4 = commit(a3

		rw.sort(RevSort.TOPO);
		markStart(a4);
		assertCommit(a4
		assertCommit(b3
		assertCommit(a3
		assertCommit(b2
		assertCommit(a2
		assertCommit(b1
		assertCommit(a1
		assertNull(rw.next());
	}

	@Test
	public void testSort_TOPO_REVERSE_MultipleLinesOfHistory()
			throws Exception {
		final RevCommit a1 = commit();
		final RevCommit b1 = commit(a1);
		final RevCommit a2 = commit(a1
		final RevCommit b2 = commit(b1);
		final RevCommit b3 = commit(b1);
		final RevCommit a3 = commit(a2
		final RevCommit a4 = commit(a3

		rw.sort(RevSort.TOPO);
		rw.sort(RevSort.REVERSE
		markStart(a4);
		assertCommit(a1
		assertCommit(b1
		assertCommit(a2
		assertCommit(b2
		assertCommit(a3
		assertCommit(b3
		assertCommit(a4
		assertNull(rw.next());
	}

	@Test
	public void testSort_TOPO_ParentOfMultipleStartChildren() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(a);
		final RevCommit d1 = commit(a);
		final RevCommit d2 = commit(d1);
		final RevCommit e = commit(a);

		rw.sort(RevSort.TOPO);
		markStart(b);
		markStart(c);
		markStart(d2);
		markStart(e);
		assertCommit(e
		assertCommit(d2
		assertCommit(d1
		assertCommit(c
		assertCommit(b
		assertCommit(a
		assertNull(rw.next());
	}
