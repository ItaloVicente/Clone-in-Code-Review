		commit = log.next();
		assertTrue(commits.contains(commit));
		assertEquals("commit#2"
		assertFalse(log.hasNext());
	}

	@Test
	public void logAllCommitsWithSkip() throws Exception {
		Git git = Git.wrap(db);
		List<RevCommit> commits = createCommits(git);

		Iterator<RevCommit> log = git.log().all().setSkip(1).call().iterator();
		assertTrue(log.hasNext());
		RevCommit commit = log.next();
		assertTrue(commits.contains(commit));
		assertEquals("commit#2"
		assertTrue(log.hasNext());
		commit = log.next();
		assertTrue(commits.contains(commit));
		assertEquals("commit#1"
		assertFalse(log.hasNext());
	}

	@Test
	public void logAllCommitsWithSkipAndMaxCount() throws Exception {
		Git git = Git.wrap(db);
		List<RevCommit> commits = createCommits(git);

		Iterator<RevCommit> log = git.log().all().setSkip(1).setMaxCount(1).call()
				.iterator();
		assertTrue(log.hasNext());
		RevCommit commit = log.next();
		assertTrue(commits.contains(commit));
		assertEquals("commit#2"
