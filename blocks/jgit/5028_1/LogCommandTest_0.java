	@Test
	public void logPathWithMaxCount() throws Exception {
		Git git = Git.wrap(db);
		List<RevCommit> commits = createCommits(git);

		Iterator<RevCommit> log = git.log().addPath("Test.txt").setMaxCount(1)
				.call().iterator();
		assertTrue(log.hasNext());
		RevCommit commit = log.next();
		assertTrue(commits.contains(commit));
		assertEquals("commit#2"
		assertFalse(log.hasNext());
	}

	@Test
	public void logPathWithSkip() throws Exception {
		Git git = Git.wrap(db);
		List<RevCommit> commits = createCommits(git);

		Iterator<RevCommit> log = git.log().addPath("Test.txt").setSkip(1)
				.call().iterator();
		assertTrue(log.hasNext());
		RevCommit commit = log.next();
		assertTrue(commits.contains(commit));
		assertEquals("commit#1"
		assertFalse(log.hasNext());
	}

