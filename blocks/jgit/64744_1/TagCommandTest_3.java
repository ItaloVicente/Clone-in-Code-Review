		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();
			try {
				git.tag().setMessage("some message").call();
				fail("We should have failed without a tag name");
			} catch (InvalidTagNameException e) {
			}
