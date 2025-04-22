		Git git = new Git(db);

		writeTrashFile("a", "1\na\n3\n");
		git.add().addFilepattern("a").call();
		RevCommit initialCommit = git.commit().setMessage("initial").call();

		createBranch(initialCommit, "refs/heads/side");
		checkoutBranch("refs/heads/side");

		assertTrue(new File(db.getWorkTree(), "a").delete());
		git.add().addFilepattern("a").setUpdate(true).call();
		RevCommit secondCommit = git.commit().setMessage("side").call();

		assertFalse(new File(db.getWorkTree(), "a").exists());
		checkoutBranch("refs/heads/master");
		assertTrue(new File(db.getWorkTree(), "a").exists());

		assertTrue(new File(db.getWorkTree(), "a").delete());
		git.add().addFilepattern("a").setUpdate(true).call();
		git.commit().setMessage("main").call();

		MergeResult result = git.merge().include(secondCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.MERGED, result.getMergeStatus());
