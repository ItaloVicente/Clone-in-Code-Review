	@Test
	public void testShallowCommitParse() throws Exception {
		RevCommit a = commit();
		RevCommit b = commit(a);

		createShallowFile(b);

		rw.close();
		rw = createRevWalk();
		b = rw.parseCommit(b);

		markStart(b);
		assertCommit(b
		assertNull(rw.next());
	}

