
	@Test
	public void testCommitHeaders_rewrittenParents() throws Exception {
		final RevCommit a = commit(tree(file("d/f"
		final RevCommit b = commit(tree(file("d/f"
		final RevCommit c = commit(tree(file("d/f"
		filter("d/f");
		markStart(c);

		RevCommit cBar = rw.next();
		assertNotNull(cBar.getShortMessage());
		assertEquals(cBar.getCommitTime()

		RevCommit aBar = rw.next();
		assertNotNull(aBar.getShortMessage());
		assertEquals(aBar.getCommitTime()

		assertNull(rw.next());
	}

	@Test
	public void testFlags_rewrittenParents() throws Exception {
		final RevCommit a = commit(tree(file("d/f"
		final RevCommit b = commit(tree(file("d/f"
		final RevCommit c = commit(tree(file("d/f"

		final RevFlag flag1 = rw.newFlag("flag1");
		final RevFlag flag2 = rw.newFlag("flag2");

		a.add(flag1);
		c.add(flag2);

		filter("d/f");
		markStart(c);

		RevCommit cBar = rw.next();
		assertEquals(cBar.flags & RevObject.PARSED
		assertEquals(cBar.flags & flag2.mask

		RevCommit aBar = rw.next();
		assertEquals(aBar.flags & RevObject.PARSED
		assertEquals(aBar.flags & flag1.mask

		assertNull(rw.next());
	}
