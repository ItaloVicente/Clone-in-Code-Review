	@Test
	public void addRangeWithMerge() throws Exception{
		String fileA = "fileA";
		String fileB = "fileB";
		Git git = Git.wrap(db);

		writeTrashFile(fileA
		git.add().addFilepattern(fileA).call();
		git.commit().setMessage("commit a").call();

		writeTrashFile(fileA
		git.add().addFilepattern(fileA).call();
		RevCommit b = git.commit().setMessage("commit b").call();

		writeTrashFile(fileA
		git.add().addFilepattern(fileA).call();
		RevCommit c = git.commit().setMessage("commit c").call();

		createBranch(b
		checkoutBranch("refs/heads/side");

		writeTrashFile(fileB
		git.add().addFilepattern(fileB).call();
		RevCommit d = git.commit().setMessage("commit d").call();

		checkoutBranch("refs/heads/master");
		MergeResult m = git.merge().include(d.getId()).call();
		assertEquals(MergeResult.MergeStatus.MERGED

		Iterator<RevCommit> rangeLog = git.log().addRange(b.getId()

		RevCommit commit = rangeLog.next();
		assertEquals(m.getNewHead()
		commit = rangeLog.next();
		assertEquals(c.getId()
		commit = rangeLog.next();
		assertEquals(d.getId()
		assertFalse(rangeLog.hasNext());
	}

