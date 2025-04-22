	public void testDeletionAndConflict() throws Exception {
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
		writeTrashFile("a"
		git.add().addFilepattern("b").setUpdate(true).call();
		git.add().addFilepattern("a").setUpdate(true).call();
		RevCommit secondCommit = git.commit().setMessage("side").call();

		assertFalse(new File(db.getWorkTree()
		checkoutBranch("refs/heads/master");
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("a"
		writeTrashFile("c/c/c"
		git.add().addFilepattern("a").addFilepattern("c/c/c").call();
		git.commit().setMessage("main").call();

		MergeResult result = git.merge().include(secondCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.CONFLICTING

		assertEquals(
				"1\na\n<<<<<<< HEAD\n3(main)\n=======\n3(side)\n>>>>>>> 54ffed45d62d252715fc20e41da92d44c48fb0ff\n"
				read(new File(db.getWorkTree()
		assertFalse(new File(db.getWorkTree()
		assertEquals("1\nc(main)\n3\n"
				read(new File(db.getWorkTree()
	}

