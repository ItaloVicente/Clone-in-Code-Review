		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();
			try {
				git.tag().setSigned(true).setName("tag").call();
				fail("We should have failed with an UnsupportedOperationException due to signed tag");
			} catch (UnsupportedOperationException e) {
			}
