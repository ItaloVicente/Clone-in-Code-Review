		try (Git git = new Git(db)) {
			CommitCommand commitCmd = git.commit();
			commitCmd.setMessage("initial commit").call();
			try {
				commitCmd.setAuthor(author);
				fail("didn't catch the expected exception");
			} catch (IllegalStateException e) {
			}
			LogCommand logCmd = git.log();
