	@Test
	public void testAddRangeWithMerge() throws Exception{
		String file1 = "file1";
		Git git = Git.wrap(db);

		writeTrashFile(file1
		git.add().addFilepattern(file1).call();
		RevCommit a = git.commit().setMessage("commit a").call();

		createBranch(a

		writeTrashFile(file1
		writeTrashFile("conflict"
		git.add().addFilepattern(".").call();
		RevCommit b = git.commit().setMessage("commit b").call();

		checkoutBranch("refs/heads/topic");

		writeTrashFile("file3"
		git.add().addFilepattern("file3").call();
		RevCommit c = git.commit().setMessage("commit c").call();

		createBranch(c

		writeTrashFile("file2"
		writeTrashFile("conflict"
		git.add().addFilepattern(".").call();
		RevCommit d = git.commit().setMessage("commit d").call();
		assertTrue(new File(db.getWorkTree()


		checkoutBranch("refs/heads/side");
		writeTrashFile("file3"
		writeTrashFile("conflict"
		git.add().addFilepattern(".").call();
		RevCommit e = git.commit().setMessage("commit e").call();


		checkoutBranch("refs/heads/topic");
		MergeResult result = git.merge().include(e.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();

		final RevCommit f;
		assertEquals(MergeResult.MergeStatus.CONFLICTING
		assertEquals(Collections.singleton("conflict")
				.getConflicting());
		writeTrashFile("conflict"
		git.add().addFilepattern("conflict").call();
		f = git.commit().setMessage("commit f").call();

		Iterator<RevCommit> log = git.log().call().iterator();
		Iterator<RevCommit> rangeLog = git.log().addRange(a.getId()

		RevCommit commit;
		commit = rangeLog.next();
		assertEquals(commit.getFullMessage()
		commit = rangeLog.next();
		assertEquals(commit.getFullMessage()
		commit = rangeLog.next();
		assertEquals(commit.getFullMessage()
		commit = rangeLog.next();
		assertEquals(commit.getFullMessage()
		assertFalse(rangeLog.hasNext());
	}

