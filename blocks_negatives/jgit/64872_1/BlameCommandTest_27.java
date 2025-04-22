		Git git = new Git(db);
		RevCommit base = commitFile("file.txt", join("0", "1", "2"), "master");
		RevCommit side = commitFile("file.txt", join("0", "1", "   2 side  "),
				"side");

		checkoutBranch("refs/heads/master");
		git.merge().setFastForward(FastForwardMode.NO_FF).include(side).call();

		writeTrashFile("file.txt", join("0", "1", "2 side"));
		RevCommit merge = git.commit().setAll(true).setMessage("merge")
				.setAmend(true)
				.call();

		BlameCommand command = new BlameCommand(db);
		command.setFilePath("file.txt")
				.setTextComparator(RawTextComparator.WS_IGNORE_ALL)
				.setStartCommit(merge.getId());
		BlameResult lines = command.call();

		assertEquals(3, lines.getResultContents().size());
		assertEquals(base, lines.getSourceCommit(0));
		assertEquals(base, lines.getSourceCommit(1));
		assertEquals(side, lines.getSourceCommit(2));
