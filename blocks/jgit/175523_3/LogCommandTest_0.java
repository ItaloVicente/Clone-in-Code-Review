	@Test
	public void addRangeWithMerge() throws Exception{
		Git git = Git.wrap(db);

		RevCommit b = git.commit().setMessage("commit b").call();
		RevCommit c = git.commit().setMessage("commit c").call();

		createBranch(b
		checkoutBranch("refs/heads/side");
		RevCommit d = git.commit().setMessage("commit d").call();

		checkoutBranch("refs/heads/master");
		MergeResult m = git.merge().include(d.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeResult.MergeStatus.MERGED

		Iterator<RevCommit> rangeLog = git.log().addRange(b.getId()

		RevCommit commit = rangeLog.next();
		assertEquals(commit.getId()
		commit = rangeLog.next();
		assertEquals(c.getFullMessage()
		commit = rangeLog.next();
		assertEquals(d.getFullMessage()
		assertFalse(rangeLog.hasNext());
	}

