		Git git = new Git(db);

		writeTrashFile("a", "1\na\n3\n");
		writeTrashFile("b", "1\nb\n3\n");
		writeTrashFile("c/c/c", "1\nc\n3\n");
		git.add().addFilepattern("a").addFilepattern("b")
				.addFilepattern("c/c/c").call();
		RevCommit initialCommit = git.commit().setMessage("initial").call();

		createBranch(initialCommit, "refs/heads/side");
		checkoutBranch("refs/heads/side");

		writeTrashFile("a", "1\na(side)\n3\n");
		writeTrashFile("b", "1\nb(side)\n3\n");
		git.add().addFilepattern("a").addFilepattern("b").call();
		RevCommit secondCommit = git.commit().setMessage("side").call();

		assertEquals("1\nb(side)\n3\n", read(new File(db.getWorkTree(), "b")));
		checkoutBranch("refs/heads/master");
		assertEquals("1\nb\n3\n", read(new File(db.getWorkTree(), "b")));

		writeTrashFile("a", "1\na(main)\n3\n");
		writeTrashFile("c/c/c", "1\nc(main)\n3\n");
		git.add().addFilepattern("a").addFilepattern("c/c/c").call();
		git.commit().setMessage("main").call();

		MergeResult result = git.merge().include(secondCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.CONFLICTING, result.getMergeStatus());

		assertEquals(
				"1\n<<<<<<< HEAD\na(main)\n=======\na(side)\n>>>>>>> 86503e7e397465588cc267b65d778538bffccb83\n3\n",
				read(new File(db.getWorkTree(), "a")));
		assertEquals("1\nb(side)\n3\n", read(new File(db.getWorkTree(), "b")));
		assertEquals("1\nc(main)\n3\n",
				read(new File(db.getWorkTree(), "c/c/c")));

		assertEquals(1, result.getConflicts().size());
		assertEquals(3, result.getConflicts().get("a")[0].length);

		assertEquals(RepositoryState.MERGING, db.getRepositoryState());
