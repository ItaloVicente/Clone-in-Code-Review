		Git git = new Git(db);

		RevCommit base = commitFile("file.txt", join("0", "1", "2", "3", "4"),
				"master");

		git.checkout().setName("side").setCreateBranch(true)
				.setStartPoint(base).call();
		RevCommit side = commitFile("file.txt",
				join("0", "1 side", "2", "3 on side", "4"), "side");

		commitFile("file.txt", join("0", "1", "2"), "master");

		checkoutBranch("refs/heads/master");
		git.merge().include(side).call();

		RevCommit merge = commitFile("file.txt",
				join("0", "1 side", "2", "3 resolved", "4"), "master");

		BlameCommand command = new BlameCommand(db);
		command.setFilePath("file.txt");
		BlameResult lines = command.call();

		assertEquals(5, lines.getResultContents().size());
		assertEquals(base, lines.getSourceCommit(0));
		assertEquals(side, lines.getSourceCommit(1));
		assertEquals(base, lines.getSourceCommit(2));
		assertEquals(merge, lines.getSourceCommit(3));
		assertEquals(base, lines.getSourceCommit(4));
