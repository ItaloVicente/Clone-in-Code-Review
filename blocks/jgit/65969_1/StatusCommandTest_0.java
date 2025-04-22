		try (Git git = new Git(db)) {
			Status stat = git.status().call();
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0
		}
