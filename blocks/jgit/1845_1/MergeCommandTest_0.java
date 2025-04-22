	public void testSingleDeletion() throws Exception {
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

		assertTrue(new File(db.getWorkTree()
		git.add().addFilepattern("b").setUpdate(true).call();
		RevCommit secondCommit = git.commit().setMessage("side").call();

		assertFalse(new File(db.getWorkTree()
		checkoutBranch("refs/heads/master");
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("a"
		writeTrashFile("c/c/c"
		git.add().addFilepattern("a").addFilepattern("c/c/c").call();
		RevCommit thirdCommit = git.commit().setMessage("main").call();

		MergeResult result = git.merge().include(secondCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.MERGED

		assertEquals("1\na\n3(main)\n"
		assertFalse(new File(db.getWorkTree()
		assertEquals("1\nc(main)\n3\n"
				read(new File(db.getWorkTree()

		checkoutBranch("refs/heads/side");
		assertFalse(new File(db.getWorkTree()

		result = git.merge().include(thirdCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.MERGED

		assertEquals("1\na\n3(main)\n"
		assertFalse(new File(db.getWorkTree()
		assertEquals("1\nc(main)\n3\n"
				read(new File(db.getWorkTree()
	}

