		try (Git git = new Git(db)) {
				git.branchCreate().setName("initial").call();
			RevCommit second = git.commit().setMessage("second commit")
					.call();
			assertArrayOfLinesEquals(new String[] { "  initial"
					execute("git branch --contains 6fd41be"));
			assertArrayOfLinesEquals(new String[] { "* master"
					execute("git branch --contains " + second.name()));
		}
