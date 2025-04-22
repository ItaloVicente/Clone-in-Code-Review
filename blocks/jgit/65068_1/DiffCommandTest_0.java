		try (Git git = new Git(db)) {
			git.add().addFilepattern(".").call();
			git.commit().setMessage("Initial commit").call();
			write(new File(folder

			OutputStream out = new ByteArrayOutputStream();
			List<DiffEntry> entries = git.diff().setOutputStream(out).call();
			assertEquals(1
			assertEquals(ChangeType.MODIFY
					.getChangeType());
			assertEquals("folder/folder.txt"
					.getOldPath());
			assertEquals("folder/folder.txt"
					.getNewPath());

			String actual = out.toString();
			String expected = "diff --git a/folder/folder.txt b/folder/folder.txt\n"
					+ "index 0119635..95c4c65 100644\n"
					+ "--- a/folder/folder.txt\n"
					+ "+++ b/folder/folder.txt\n"
					+ "@@ -1 +1 @@\n"
					+ "-folder\n"
					+ "\\ No newline at end of file\n"
					+ "+folder change\n"
					+ "\\ No newline at end of file\n";
			assertEquals(expected.toString()
		}
