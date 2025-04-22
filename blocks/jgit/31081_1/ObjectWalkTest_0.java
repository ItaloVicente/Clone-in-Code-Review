	@Test
	public void testMarkUninterestingPropagation() throws Exception {
		final RevBlob f = blob("1");
		final RevTree t = tree(file("f"
		final RevCommit c1 = commit(t);
		final RevCommit c2 = commit(t);

		markUninteresting(c1);
		markStart(c2);

		assertSame(c2
		assertNull(objw.next());
		assertNull(objw.nextObject());
	}

