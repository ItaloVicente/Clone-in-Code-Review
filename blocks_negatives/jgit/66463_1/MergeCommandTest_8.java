		Git git = new Git(db);

		writeTrashFile("a", "1\na\n3\n");
		writeTrashFile("b", "1\nb\n3\n");
		writeTrashFile("c/c/c", "1\nc\n3\n");
		git.add().addFilepattern("a").addFilepattern("b")
				.addFilepattern("c/c/c").call();
		RevCommit initialCommit = git.commit().setMessage("initial").call();

		createBranch(initialCommit, "refs/heads/side");
		checkoutBranch("refs/heads/side");

		writeTrashFile("a", "1(side)\na\n3\n");
		writeTrashFile("b", "1\nb(side)\n3\n");
		git.add().addFilepattern("a").addFilepattern("b").call();
		RevCommit secondCommit = git.commit().setMessage("side").call();

		assertEquals("1\nb(side)\n3\n", read(new File(db.getWorkTree(), "b")));
		checkoutBranch("refs/heads/master");
		assertEquals("1\nb\n3\n", read(new File(db.getWorkTree(), "b")));

		writeTrashFile("a", "1\na\n3(main)\n");
		writeTrashFile("c/c/c", "1\nc(main)\n3\n");
		git.add().addFilepattern("a").addFilepattern("c/c/c").call();
		RevCommit thirdCommit = git.commit().setMessage("main").call();

		MergeResult result = git.merge().include(secondCommit.getId())
				.setCommit(false)
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.MERGED_NOT_COMMITTED, result.getMergeStatus());
		assertEquals(db.exactRef(Constants.HEAD).getTarget().getObjectId(),
				thirdCommit.getId());

		assertEquals("1(side)\na\n3(main)\n", read(new File(db.getWorkTree(),
				"a")));
		assertEquals("1\nb(side)\n3\n", read(new File(db.getWorkTree(), "b")));
		assertEquals("1\nc(main)\n3\n",
				read(new File(db.getWorkTree(), "c/c/c")));

		assertEquals(null, result.getConflicts());

		assertEquals(2, result.getMergedCommits().length);
		assertEquals(thirdCommit, result.getMergedCommits()[0]);
		assertEquals(secondCommit, result.getMergedCommits()[1]);
		assertNull(result.getNewHead());
		assertEquals(RepositoryState.MERGING_RESOLVED, db.getRepositoryState());
