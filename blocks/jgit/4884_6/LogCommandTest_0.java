		commit = log.next();
		assertTrue(commits.contains(commit));
		assertEquals("commit#2"
		assertFalse(log.hasNext());
	}

	@Test
	public void logAllCommitsWithSkip() throws Exception {
		List<RevCommit> commits = new ArrayList<RevCommit>();
		Git git = Git.wrap(db);

		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		commits.add(git.commit().setMessage("commit#1").call());

		writeTrashFile("Test1.txt"
		git.add().addFilepattern("Test1.txt").call();
		commits.add(git.commit().setMessage("commit#2").call());

		writeTrashFile("Test2.txt"
		git.add().addFilepattern("Test2.txt").call();
		commits.add(git.commit().setMessage("commit#3").call());

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
		List<RevCommit> commits = new ArrayList<RevCommit>();
		Git git = Git.wrap(db);

		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		commits.add(git.commit().setMessage("commit#1").call());

		writeTrashFile("Test1.txt"
		git.add().addFilepattern("Test1.txt").call();
		commits.add(git.commit().setMessage("commit#2").call());

		writeTrashFile("Test2.txt"
		git.add().addFilepattern("Test2.txt").call();
		commits.add(git.commit().setMessage("commit#3").call());

		Iterator<RevCommit> log = git.log().all().setSkip(1).setMaxCount(1).call()
				.iterator();
		assertTrue(log.hasNext());
		RevCommit commit = log.next();
		assertTrue(commits.contains(commit));
		assertEquals("commit#2"
