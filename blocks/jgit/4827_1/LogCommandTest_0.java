
	@Test
	public void logAllCommitsWithMaxCount() throws Exception {
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

		Iterator<RevCommit> log = git.log().all().setMaxCount(2).call()
				.iterator();
		assertTrue(log.hasNext());
		assertTrue(commits.contains(log.next()));
		assertTrue(log.hasNext());
		assertTrue(commits.contains(log.next()));
		assertFalse(log.hasNext());
	}
