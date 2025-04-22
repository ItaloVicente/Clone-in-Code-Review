		try (Git git = new Git(db)) {
			RevCommit base = commitFile("file.txt"
					"master");

			git.checkout().setName("side").setCreateBranch(true)
					.setStartPoint(base).call();
			RevCommit side = commitFile("file.txt"
					join("0"

			commitFile("file.txt"

			checkoutBranch("refs/heads/master");
			git.merge().include(side).call();

			RevCommit merge = commitFile("file.txt"
					join("0"

			BlameCommand command = new BlameCommand(db);
			command.setFilePath("file.txt");
			BlameResult lines = command.call();

			assertEquals(5
			assertEquals(base
			assertEquals(side
			assertEquals(base
			assertEquals(merge
			assertEquals(base
		}
