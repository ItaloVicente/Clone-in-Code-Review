		try (Git git = new Git(db)) {
			git.add().addFilepattern(".").call();
			write(file

			List<DiffEntry> diffs = git.diff().call();
			assertNotNull(diffs);
			assertEquals(1
			DiffEntry diff = diffs.get(0);
			assertEquals(ChangeType.MODIFY
			assertEquals("test.txt"
			assertEquals("test.txt"
		}
