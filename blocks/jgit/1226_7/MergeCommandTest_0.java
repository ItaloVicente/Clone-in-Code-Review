
	public void testContentMerge() throws Exception {
		Git git = new Git(db);

		writeTrashFile("a"
		writeTrashFile("b"
		writeTrashFile("c/c/c"
		git.add().addFilepattern("a").addFilepattern("b")
				.addFilepattern("c/c/c").call();
		RevCommit initialCommit = git.commit().setMessage("initial").call();

		createBranch(initialCommit
		checkoutBranch("refs/heads/side");

		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern("a").addFilepattern("b").call();
		RevCommit secondCommit = git.commit().setMessage("side").call();

		assertEquals("1\nb(side)\n3\n"
		checkoutBranch("refs/heads/master");
		assertEquals("1\nb\n3\n"

		writeTrashFile("a"
		writeTrashFile("c/c/c"
		git.add().addFilepattern("a").addFilepattern("c/c/c").call();
		git.commit().setMessage("main").call();

		MergeResult result = git.merge().include(secondCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.CONFLICTING

		assertEquals(
				"1\n<<<<<<< HEAD\na(main)\n=======\na(side)\n>>>>>>> 86503e7e397465588cc267b65d778538bffccb83\n3\n"
				read(new File(db.getWorkTree()
		assertEquals("1\nb(side)\n3\n"
		assertEquals("1\nc(main)\n3\n"
				read(new File(db.getWorkTree()

		assertEquals(1
		assertEquals(3

		assertEquals(RepositoryState.MERGING
	}

	public void testSuccessfulContentMerge() throws Exception {
		Git git = new Git(db);

		writeTrashFile("a"
		writeTrashFile("b"
		writeTrashFile("c/c/c"
		git.add().addFilepattern("a").addFilepattern("b")
				.addFilepattern("c/c/c").call();
		RevCommit initialCommit = git.commit().setMessage("initial").call();

		createBranch(initialCommit
		checkoutBranch("refs/heads/side");

		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern("a").addFilepattern("b").call();
		RevCommit secondCommit = git.commit().setMessage("side").call();

		assertEquals("1\nb(side)\n3\n"
		checkoutBranch("refs/heads/master");
		assertEquals("1\nb\n3\n"

		writeTrashFile("a"
		writeTrashFile("c/c/c"
		git.add().addFilepattern("a").addFilepattern("c/c/c").call();
		RevCommit thirdCommit = git.commit().setMessage("main").call();

		MergeResult result = git.merge().include(secondCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.MERGED

		assertEquals("1(side)\na\n3(main)\n"
				"a")));
		assertEquals("1\nb(side)\n3\n"
		assertEquals("1\nc(main)\n3\n"
				"c/c/c")));

		assertEquals(null

		assertTrue(2 == result.getMergedCommits().length);
		assertEquals(thirdCommit
		assertEquals(secondCommit

		Iterator<RevCommit> it = git.log().call().iterator();
		RevCommit newHead = it.next();
		assertEquals(newHead
		assertEquals(2
		assertEquals(thirdCommit
		assertEquals(secondCommit
		assertEquals(
				"merging 3fa334456d236a92db020289fe0bf481d91777b4 into HEAD"
				newHead.getFullMessage());
		assertEquals(RepositoryState.SAFE
	}

	public void testSuccessfulContentMergeAndDirtyworkingTree()
			throws Exception {
		Git git = new Git(db);

		writeTrashFile("a"
		writeTrashFile("b"
		writeTrashFile("d"
		writeTrashFile("c/c/c"
		git.add().addFilepattern("a").addFilepattern("b")
				.addFilepattern("c/c/c").addFilepattern("d").call();
		RevCommit initialCommit = git.commit().setMessage("initial").call();

		createBranch(initialCommit
		checkoutBranch("refs/heads/side");

		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern("a").addFilepattern("b").call();
		RevCommit secondCommit = git.commit().setMessage("side").call();

		assertEquals("1\nb(side)\n3\n"
		checkoutBranch("refs/heads/master");
		assertEquals("1\nb\n3\n"

		writeTrashFile("a"
		writeTrashFile("c/c/c"
		git.add().addFilepattern("a").addFilepattern("c/c/c").call();
		RevCommit thirdCommit = git.commit().setMessage("main").call();

		writeTrashFile("d"
		MergeResult result = git.merge().include(secondCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.MERGED

		assertEquals("1(side)\na\n3(main)\n"
				"a")));
		assertEquals("1\nb(side)\n3\n"
		assertEquals("1\nc(main)\n3\n"
				"c/c/c")));

		assertEquals(null

		assertTrue(2 == result.getMergedCommits().length);
		assertEquals(thirdCommit
		assertEquals(secondCommit

		Iterator<RevCommit> it = git.log().call().iterator();
		RevCommit newHead = it.next();
		assertEquals(newHead
		assertEquals(2
		assertEquals(thirdCommit
		assertEquals(secondCommit
		assertEquals(
				"merging 064d54d98a4cdb0fed1802a21c656bfda67fe879 into HEAD"
				newHead.getFullMessage());

		assertEquals(RepositoryState.SAFE
	}

	public void testMergeFailingWithDirtyWorkingTree() throws Exception {
		Git git = new Git(db);

		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern("a").addFilepattern("b").call();
		RevCommit initialCommit = git.commit().setMessage("initial").call();

		createBranch(initialCommit
		checkoutBranch("refs/heads/side");

		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern("a").addFilepattern("b").call();
		RevCommit secondCommit = git.commit().setMessage("side").call();

		assertEquals("1\nb(side)\n3\n"
		checkoutBranch("refs/heads/master");
		assertEquals("1\nb\n3\n"

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("main").call();

		writeTrashFile("a"
		MergeResult result = git.merge().include(secondCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();

		assertEquals(MergeStatus.FAILED

		assertEquals("--- dirty ---"
		assertEquals("1\nb\n3\n"

		assertEquals(null

		assertEquals(RepositoryState.SAFE
	}

	public void testMergeConflictFileFolder() throws Exception {
		Git git = new Git(db);

		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern("a").addFilepattern("b").call();
		RevCommit initialCommit = git.commit().setMessage("initial").call();

		createBranch(initialCommit
		checkoutBranch("refs/heads/side");

		writeTrashFile("c/c/c"
		writeTrashFile("d"
		git.add().addFilepattern("c/c/c").addFilepattern("d").call();
		RevCommit secondCommit = git.commit().setMessage("side").call();

		checkoutBranch("refs/heads/master");

		writeTrashFile("c"
		writeTrashFile("d/d/d"
		git.add().addFilepattern("c").addFilepattern("d/d/d").call();
		git.commit().setMessage("main").call();

		MergeResult result = git.merge().include(secondCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();

		assertEquals(MergeStatus.CONFLICTING

		assertEquals("1\na\n3\n"
		assertEquals("1\nb\n3\n"
		assertEquals("1\nc(main)\n3\n"
		assertEquals("1\nd(main)\n3\n"

		assertEquals(null

		assertEquals(RepositoryState.MERGING
	}

