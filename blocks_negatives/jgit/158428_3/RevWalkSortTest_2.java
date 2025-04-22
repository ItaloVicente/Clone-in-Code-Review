
        @Test
	public void testSort_TOPO_OutOfOrderCommitTimes() throws Exception {
		final RevCommit a = commit();
		final RevCommit c1 = commit(a);
		final RevCommit b = commit(a);
		final RevCommit c2 = commit(c1);
		final RevCommit d = commit(b, c2);

		rw.sort(RevSort.TOPO);
		markStart(d);
		assertCommit(d, rw.next());
		assertCommit(c2, rw.next());
		assertCommit(c1, rw.next());
		assertCommit(b, rw.next());
		assertCommit(a, rw.next());
		assertNull(rw.next());
	}

	@Test
	public void testSort_TOPO_MultipleLinesOfHistory() throws Exception {
		final RevCommit a1 = commit();
		final RevCommit b1 = commit(a1);
		final RevCommit a2 = commit(a1, b1);
		final RevCommit b2 = commit(b1);
		final RevCommit b3 = commit(b1);
		final RevCommit a3 = commit(a2, b2);
		final RevCommit a4 = commit(a3, b3);

		rw.sort(RevSort.TOPO);
		markStart(a4);
		assertCommit(a4, rw.next());
		assertCommit(b3, rw.next());
		assertCommit(a3, rw.next());
		assertCommit(b2, rw.next());
		assertCommit(a2, rw.next());
		assertCommit(b1, rw.next());
		assertCommit(a1, rw.next());
		assertNull(rw.next());
	}

	@Test
	public void testSort_TOPO_REVERSE_MultipleLinesOfHistory()
			throws Exception {
		final RevCommit a1 = commit();
		final RevCommit b1 = commit(a1);
		final RevCommit a2 = commit(a1, b1);
		final RevCommit b2 = commit(b1);
		final RevCommit b3 = commit(b1);
		final RevCommit a3 = commit(a2, b2);
		final RevCommit a4 = commit(a3, b3);

		rw.sort(RevSort.TOPO);
		rw.sort(RevSort.REVERSE, true);
		markStart(a4);
		assertCommit(a1, rw.next());
		assertCommit(b1, rw.next());
		assertCommit(a2, rw.next());
		assertCommit(b2, rw.next());
		assertCommit(a3, rw.next());
		assertCommit(b3, rw.next());
		assertCommit(a4, rw.next());
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
		assertCommit(e, rw.next());
		assertCommit(d2, rw.next());
		assertCommit(d1, rw.next());
		assertCommit(c, rw.next());
		assertCommit(b, rw.next());
		assertCommit(a, rw.next());
		assertNull(rw.next());
	}

	@Test
	public void testSort_TOPO_Uninteresting() throws Exception {
		final RevCommit a1 = commit();
		final RevCommit a2 = commit(a1);
		final RevCommit a3 = commit(a2);
		final RevCommit b = commit(a1);
		final RevCommit a4 = commit(a3, b);

		rw.sort(RevSort.TOPO);
		markStart(a4);
		markUninteresting(a2);
		assertCommit(a4, rw.next());
		assertCommit(b, rw.next());
		assertCommit(a3, rw.next());
		assertNull(rw.next());
	}
