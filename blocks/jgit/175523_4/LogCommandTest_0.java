	@Test
	public void addRangeWithMerge() throws Exception{
		String file = "file";
		Git git = Git.wrap(db);

		writeTrashFile(file
		git.add().addFilepattern(file).call();

		writeTrashFile(file
		git.add().addFilepattern(file).call();
		RevCommit b = git.commit().setMessage("commit b").call();

		writeTrashFile(file
		git.add().addFilepattern(file).call();
		RevCommit c = git.commit().setMessage("commit c").call();

		createBranch(b
		checkoutBranch("refs/heads/side");

		writeTrashFile(file
		git.add().addFilepattern(file).call();
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

