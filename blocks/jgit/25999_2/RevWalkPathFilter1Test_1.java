	@Test
	public void testStringOfPearls_FilePath2_NoParentRewriting()
	throws Exception {
		final RevCommit a = commit(tree(file("d/f"
		final RevCommit b = commit(tree(file("d/f"
		final RevCommit c = commit(tree(file("d/f"
		final RevCommit d = commit(tree(file("d/f"
		filter("d/f");
		markStart(d);
		rw.setRewriteParents(false);

		assertCommit(c
		assertEquals(1
		assertCommit(b

		assertCommit(a
		assertEquals(0
		assertNull(rw.next());
	}

