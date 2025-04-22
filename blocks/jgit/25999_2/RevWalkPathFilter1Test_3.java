
	@Test
	public void testStringOfPearls_FilePath3_NoParentRewriting()
			throws Exception {
		final RevCommit a = commit(tree(file("d/f"
		final RevCommit b = commit(tree(file("d/f"
		final RevCommit c = commit(tree(file("d/f"
		final RevCommit d = commit(tree(file("d/f"
		final RevCommit e = commit(tree(file("d/f"
		final RevCommit f = commit(tree(file("d/f"
		final RevCommit g = commit(tree(file("d/f"
		final RevCommit h = commit(tree(file("d/f"
		final RevCommit i = commit(tree(file("d/f"
		filter("d/f");
		markStart(i);
		rw.setRewriteParents(false);

		assertCommit(i
		assertEquals(1
		assertCommit(h

		assertCommit(c
		assertEquals(1
		assertCommit(b

		assertCommit(a
		assertEquals(0
		assertNull(rw.next());
	}
