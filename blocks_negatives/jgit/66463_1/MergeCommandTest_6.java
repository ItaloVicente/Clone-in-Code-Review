		Git git = new Git(db);

		writeTrashFile("a", "1\na\n3\n");
		git.add().addFilepattern("a").call();
		RevCommit initialCommit = git.commit().setMessage("initial").call();

		createBranch(initialCommit, "refs/heads/side");
		checkoutBranch("refs/heads/side");

		writeTrashFile("b", "1\nb(1)\n3\n");
		git.add().addFilepattern("b").call();
		RevCommit secondCommit = git.commit().setMessage("side").call();

		checkoutBranch("refs/heads/master");

		writeTrashFile("b", "1\nb(1)\n3\n");
		git.add().addFilepattern("b").call();
		git.commit().setMessage("main").call();

		MergeResult result = git.merge().include(secondCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.MERGED, result.getMergeStatus());
		assertEquals("1\nb(1)\n3\n", read(new File(db.getWorkTree(), "b")));
		assertEquals("merge " + secondCommit.getId().getName()
				+ ": Merge made by resolve.", db
				.getReflogReader(Constants.HEAD)
				.getLastEntry().getComment());
		assertEquals("merge " + secondCommit.getId().getName()
				+ ": Merge made by resolve.", db
				.getReflogReader(db.getBranch())
				.getLastEntry().getComment());
