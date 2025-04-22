		Git git = new Git(db);

		writeTrashFile("a", "1\na\n3\n");
		writeTrashFile("b", "1\nb\n3\n");
		git.add().addFilepattern("a").addFilepattern("b").call();
		RevCommit initialCommit = git.commit().setMessage("initial").call();

		createBranch(initialCommit, "refs/heads/side");
		checkoutBranch("refs/heads/side");
		git.rm().addFilepattern("a").call();
		RevCommit secondCommit = git.commit().setMessage("side").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile("a", "1\na(main)\n3\n");
		git.add().addFilepattern("a").call();
		git.commit().setMessage("main").call();

		MergeResult result = git.merge().include(secondCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.CONFLICTING, result.getMergeStatus());

		assertTrue(new File(db.getWorkTree(), "a").exists());
		assertEquals("1\na(main)\n3\n", read(new File(db.getWorkTree(), "a")));
		assertEquals("1\nb\n3\n", read(new File(db.getWorkTree(), "b")));

		assertEquals(1, result.getConflicts().size());
		assertEquals(3, result.getConflicts().get("a")[0].length);
