		Git git = new Git(db);
		git.add().addFilepattern(".").call();
		write(file, "b");

		List<DiffEntry> diffs = git.diff().call();
		assertNotNull(diffs);
		assertEquals(1, diffs.size());
		DiffEntry diff = diffs.get(0);
		assertEquals(ChangeType.MODIFY, diff.getChangeType());
		assertEquals("test.txt", diff.getOldPath());
		assertEquals("test.txt", diff.getNewPath());
