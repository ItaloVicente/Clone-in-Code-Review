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

		writeTrashFile("d", "--- dirty ---");
		MergeResult result = git.merge().include(secondCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.MERGED, result.getMergeStatus());

		assertEquals("1(side)\na\n3(main)\n", read(new File(db.getWorkTree(),
				"a")));
		assertEquals("1\nb(side)\n3\n", read(new File(db.getWorkTree(), "b")));
		assertEquals("1\nc(main)\n3\n", read(new File(db.getWorkTree(),
				"c/c/c")));
		assertEquals("--- dirty ---", read(new File(db.getWorkTree(), "d")));

		assertEquals(null, result.getConflicts());

		assertEquals(2, result.getMergedCommits().length);
		assertEquals(thirdCommit, result.getMergedCommits()[0]);
		assertEquals(secondCommit, result.getMergedCommits()[1]);

		Iterator<RevCommit> it = git.log().call().iterator();
		RevCommit newHead = it.next();
		assertEquals(newHead, result.getNewHead());
		assertEquals(2, newHead.getParentCount());
		assertEquals(thirdCommit, newHead.getParent(0));
		assertEquals(secondCommit, newHead.getParent(1));
		assertEquals(
				"Merge commit '064d54d98a4cdb0fed1802a21c656bfda67fe879'",
				newHead.getFullMessage());

		assertEquals(RepositoryState.SAFE, db.getRepositoryState());
