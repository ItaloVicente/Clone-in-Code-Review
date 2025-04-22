	@Test
	public void testTopoNonIntermixSort() throws Exception {
		RevCommit a = commit();
		RevCommit b1 = commit(a);
		RevCommit b2 = commit(a);
		RevCommit c = commit(b1

		rw.reset();
		rw.sort(RevSort.TOPO_NON_INTERMIX);
		rw.setFirstParent(true);
		markStart(c);
		assertCommit(c
		assertCommit(b1
		assertCommit(a
		assertNull(rw.next());
	}

