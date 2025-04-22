
	@Test
	public void testMaxCountRevFilter1() throws Exception {
		final RevCommit a = commit();
		final RevCommit b1 = commit(a);
		final RevCommit b2 = commit(a);
		final RevCommit c = commit(b1
		final RevCommit d = commit(c);

		rw.reset();
		rw.setRevFilter(MaxCountRevFilter.create(1
		markStart(d);
		assertCommit(c
		assertNull(rw.next());
	}

	@Test
	public void testMaxCountRevFilter2() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);

		rw.reset();
		RevFilter rf = MaxCountRevFilter.create(1
		rw.setRevFilter(
				MaxCountRevFilter.and(RevFilter.NONE
		markStart(c);
		assertNull(rw.next());
	}
