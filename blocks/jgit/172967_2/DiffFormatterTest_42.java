	@Test
	public void testTrackedFileInIgnoredFolderUnchanged()
			throws Exception {
		commitFile("empty/empty/foo"
		commitFile(".gitignore"
		try (Git git = new Git(db)) {
			Status status = git.status().call();
			assertTrue(status.isClean());
		}
		try (ByteArrayOutputStream os = new ByteArrayOutputStream();
				DiffFormatter dfmt = new DiffFormatter(os)) {
			dfmt.setRepository(db);
			dfmt.format(new DirCacheIterator(db.readDirCache())
					new FileTreeIterator(db));
			dfmt.flush();

			String actual = os.toString("UTF-8");

			assertEquals(""
		}
	}

	@Test
	public void testTrackedFileInIgnoredFolderChanged()
			throws Exception {
		String expectedDiff = "diff --git a/empty/empty/foo b/empty/empty/foo\n"
				+ "@@ -0
				+ "+changed\n";

		commitFile("empty/empty/foo"
		commitFile(".gitignore"
		try (Git git = new Git(db)) {
			Status status = git.status().call();
			assertTrue(status.isClean());
		}
		try (ByteArrayOutputStream os = new ByteArrayOutputStream();
				DiffFormatter dfmt = new DiffFormatter(os)) {
			writeTrashFile("empty/empty/foo"
			dfmt.setRepository(db);
			dfmt.format(new DirCacheIterator(db.readDirCache())
					new FileTreeIterator(db));
			dfmt.flush();

			String actual = os.toString("UTF-8");

			assertEquals(expectedDiff
		}
	}

