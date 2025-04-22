	@Test
	public void logNoMergeCommitsWithSkipAndMaxCount() throws Exception {
		setCommitsAndMerge();
		Git git = Git.wrap(db);

		Iterable<RevCommit> commits = git.log().all().call();
		Iterator<RevCommit> i = commits.iterator();
		RevCommit commit = i.next();
		assertEquals("merge s0 with m1"
		commit = i.next();
		assertEquals("s0"
		commit = i.next();
		assertEquals("m1"
		commit = i.next();
		assertEquals("m0"
		assertFalse(i.hasNext());

		commits = git.log().setRevFilter(RevFilter.NO_MERGES).setSkip(2)
				.setMaxCount(1)
				.call();
		i = commits.iterator();
		commit = i.next();
		assertEquals("m0"

		assertFalse(i.hasNext());
	}

