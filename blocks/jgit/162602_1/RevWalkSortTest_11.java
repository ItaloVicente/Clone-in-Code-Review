
	@Test
	public void testSort_TOPO_NON_INTERMIX() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c1 = commit(-5
		final RevCommit c2 = commit(10
		final RevCommit d = commit(c1

		rw.sort(RevSort.TOPO_KEEP_BRANCH_TOGETHER);
		markStart(d);
		assertCommit(d
		assertCommit(c2
		assertCommit(c1
		assertCommit(b
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testSort_TOPO_NON_INTERMIX_OutOfOrderCommitTimes()
			throws Exception {
		final RevCommit a = commit();
		final RevCommit c1 = commit(a);
		final RevCommit b = commit(a);
		final RevCommit c2 = commit(c1);
		final RevCommit d = commit(b

		rw.sort(RevSort.TOPO_KEEP_BRANCH_TOGETHER);
		markStart(d);
		assertCommit(d
		assertCommit(c2
		assertCommit(c1
		assertCommit(b
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testSort_TOPO_NON_INTERMIX_MultipleLinesOfHistory()
			throws Exception {
		final RevCommit a1 = commit();
		final RevCommit b1 = commit(a1);
		final RevCommit a2 = commit(a1
		final RevCommit b2 = commit(b1);
		final RevCommit b3 = commit(b1);
		final RevCommit a3 = commit(a2
		final RevCommit a4 = commit(a3

		rw.sort(RevSort.TOPO_KEEP_BRANCH_TOGETHER);
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
	public void testSort_TOPO_NON_INTERMIX_REVERSE() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c1 = commit(-5
		final RevCommit c2 = commit(10
		final RevCommit d = commit(c1

		rw.sort(RevSort.TOPO_KEEP_BRANCH_TOGETHER);
		rw.sort(RevSort.REVERSE
		markStart(d);
		assertCommit(a
		assertCommit(b
		assertCommit(c1
		assertCommit(c2
		assertCommit(d
		assertNull(rw.next());
	}

	@Test
	public void testSort_TOPO_NON_INTERMIX_REVERSE_MultipleLinesOfHistory()
			throws Exception {
		final RevCommit a1 = commit();
		final RevCommit b1 = commit(a1);
		final RevCommit a2 = commit(a1
		final RevCommit b2 = commit(b1);
		final RevCommit b3 = commit(b1);
		final RevCommit a3 = commit(a2
		final RevCommit a4 = commit(a3

		rw.sort(RevSort.TOPO_KEEP_BRANCH_TOGETHER);
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
	public void testSort_TOPO_NON_INTERMIX_ParentOfMultipleStartChildren()
			throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(a);
		final RevCommit d1 = commit(a);
		final RevCommit d2 = commit(d1);
		final RevCommit e = commit(a);

		rw.sort(RevSort.TOPO_KEEP_BRANCH_TOGETHER);
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

	@Test
	public void testSort_TOPO_NON_INTERMIX_Uninteresting() throws Exception {
		final RevCommit a1 = commit();
		final RevCommit a2 = commit(a1);
		final RevCommit a3 = commit(a2);
		final RevCommit b = commit(a1);
		final RevCommit a4 = commit(a3

		rw.sort(RevSort.TOPO_KEEP_BRANCH_TOGETHER);
		markStart(a4);
		markUninteresting(a2);
		assertCommit(a4
		assertCommit(b
		assertCommit(a3
		assertNull(rw.next());
	}

	@Test
	public void testSort_TOPO_NON_INTERMIX_and_TOPO_throws() throws Exception {
		final RevCommit a = commit();

		rw.sort(RevSort.TOPO_KEEP_BRANCH_TOGETHER);
		rw.sort(RevSort.TOPO
		markStart(a);
		try {
			rw.next();
			fail("did not throw IllegalStateException");
		} catch (IllegalStateException e) {
			assertEquals(
					JGitText.get().cannotCombineTopoSortWithTopoKeepBranchTogetherSort
					e.getMessage());
		}
	}
