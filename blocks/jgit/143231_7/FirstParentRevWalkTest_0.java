	@Test
	public void testFirstParentOfFirstParentMarkedUninteresting()
			throws Exception {
		RevCommit a = commit();
		RevCommit b1 = commit(a);
		RevCommit b2 = commit(a);
		RevCommit c1 = commit(b1);
		RevCommit c2 = commit(b2);
		RevCommit d = commit(c1

		rw.reset();
		rw.setFirstParent(true);
		markStart(d);
		markUninteresting(b1);
		assertCommit(d
		assertCommit(c1
		assertNull(rw.next());
	}

	@Test
	public void testFirstParentMarkedUninteresting() throws Exception {
		RevCommit a = commit();
		RevCommit b1 = commit(a);
		RevCommit b2 = commit(a);
		RevCommit c = commit(b1

		rw.reset();
		rw.setFirstParent(true);
		markStart(c);
		markUninteresting(b1);
		assertCommit(c
		assertNull(rw.next());
	}

