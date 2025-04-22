		Git git = new Git(db);

		writeTrashFile("a", "1\na\n3\n");
		writeTrashFile("b", "1\nb\n3\n");
		writeTrashFile("d", "1\nd\n3\n");
		writeTrashFile("c/c/c", "1\nc\n3\n");
		git.add().addFilepattern("a").addFilepattern("b")
				.addFilepattern("c/c/c").addFilepattern("d").call();
		RevCommit initialCommit = git.commit().setMessage("initial").call();

		createBranch(initialCommit, "refs/heads/side");
		checkoutBranch("refs/heads/side");

		assertTrue(new File(db.getWorkTree(), "b").delete());
		git.add().addFilepattern("b").setUpdate(true).call();
		RevCommit secondCommit = git.commit().setMessage("side").call();

		assertFalse(new File(db.getWorkTree(), "b").exists());
		checkoutBranch("refs/heads/master");
		assertTrue(new File(db.getWorkTree(), "b").exists());

		writeTrashFile("a", "1\na\n3(main)\n");
		writeTrashFile("c/c/c", "1\nc(main)\n3\n");
		git.add().addFilepattern("a").addFilepattern("c/c/c").call();
		RevCommit thirdCommit = git.commit().setMessage("main").call();

		MergeResult result = git.merge().include(secondCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.MERGED, result.getMergeStatus());

		assertEquals("1\na\n3(main)\n", read(new File(db.getWorkTree(), "a")));
		assertFalse(new File(db.getWorkTree(), "b").exists());
		assertEquals("1\nc(main)\n3\n",
				read(new File(db.getWorkTree(), "c/c/c")));
		assertEquals("1\nd\n3\n", read(new File(db.getWorkTree(), "d")));

		checkoutBranch("refs/heads/side");
		assertFalse(new File(db.getWorkTree(), "b").exists());

		result = git.merge().include(thirdCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.MERGED, result.getMergeStatus());

		assertEquals("1\na\n3(main)\n", read(new File(db.getWorkTree(), "a")));
		assertFalse(new File(db.getWorkTree(), "b").exists());
		assertEquals("1\nc(main)\n3\n",
				read(new File(db.getWorkTree(), "c/c/c")));
		assertEquals("1\nd\n3\n", read(new File(db.getWorkTree(), "d")));
