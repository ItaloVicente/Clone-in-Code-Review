		try (Git git = new Git(db)) {
			git.add().addFilepattern(".").call();
			git.commit().setMessage("Initial commit").call();
			write(new File(folder
			git.add().addFilepattern(".").call();

			OutputStream out = new ByteArrayOutputStream();
			List<DiffEntry> entries = git.diff().setOutputStream(out)
					.setCached(true).call();
			assertEquals(1
			assertEquals(ChangeType.ADD
					.getChangeType());
			assertEquals("/dev/null"
					.getOldPath());
			assertEquals("folder/folder.txt"
					.getNewPath());

			String actual = out.toString();
			String expected = "diff --git a/folder/folder.txt b/folder/folder.txt\n"
					+ "new file mode 100644\n"
					+ "index 0000000..0119635\n"
					+ "--- /dev/null\n"
					+ "+++ b/folder/folder.txt\n"
					+ "@@ -0
					+ "+folder\n"
					+ "\\ No newline at end of file\n";
			assertEquals(expected.toString()
		}
