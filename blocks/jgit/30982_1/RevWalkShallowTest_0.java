	@Test
	public void testEvaluateShallowWhileParsingCommit() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		final RevCommit d = commit(c);

		createShallowFile(d);

		rw = createRevWalk();

		final RevCommit commit = rw.parseCommit(d);
		assertArrayEquals(new RevCommit[0]
	}

