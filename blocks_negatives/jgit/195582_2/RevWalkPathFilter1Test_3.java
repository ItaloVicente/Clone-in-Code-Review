
	@Test
	public void testCommitHeaders_rewrittenParents() throws Exception {
		final RevCommit a = commit(tree(file("d/f", blob("a"))));
		final RevCommit b = commit(tree(file("d/f", blob("a"))), a);
		final RevCommit c = commit(tree(file("d/f", blob("b"))), b);
		filter("d/f");
		markStart(c);

		RevCommit cBar = rw.next();
		assertNotNull(cBar.getShortMessage());
		assertEquals(cBar.getCommitTime(), c.getCommitTime());

		RevCommit aBar = rw.next();
		assertNotNull(aBar.getShortMessage());
		assertEquals(aBar.getCommitTime(), a.getCommitTime());

		assertNull(rw.next());
	}

	@Test
	public void testFlags_rewrittenParents() throws Exception {
		final RevCommit a = commit(tree(file("d/f", blob("a"))));
		final RevCommit b = commit(tree(file("d/f", blob("a"))), a);
		final RevCommit c = commit(tree(file("d/f", blob("b"))), b);

		final RevFlag flag1 = rw.newFlag("flag1");
		final RevFlag flag2 = rw.newFlag("flag2");

		a.add(flag1);
		c.add(flag2);

		filter("d/f");
		markStart(c);

		RevCommit cBar = rw.next();
		assertEquals(cBar.flags & RevObject.PARSED, 1);
		assertEquals(cBar.flags & flag2.mask, flag2.mask);

		RevCommit aBar = rw.next();
		assertEquals(aBar.flags & RevObject.PARSED, 1);
		assertEquals(aBar.flags & flag1.mask, flag1.mask);

		assertNull(rw.next());
	}
