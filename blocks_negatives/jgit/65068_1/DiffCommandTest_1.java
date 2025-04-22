		Git git = new Git(db);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("Initial commit").call();
		write(new File(folder, "folder.txt"), "folder");
		git.add().addFilepattern(".").call();

		OutputStream out = new ByteArrayOutputStream();
		List<DiffEntry> entries = git.diff().setOutputStream(out)
				.setCached(true).call();
		assertEquals(1, entries.size());
		assertEquals(ChangeType.ADD, entries.get(0)
				.getChangeType());
		assertEquals("/dev/null", entries.get(0)
				.getOldPath());
		assertEquals("folder/folder.txt", entries.get(0)
				.getNewPath());

		String actual = out.toString();
		String expected = "diff --git a/folder/folder.txt b/folder/folder.txt\n"
				+ "new file mode 100644\n"
				+ "index 0000000..0119635\n"
				+ "--- /dev/null\n"
				+ "+++ b/folder/folder.txt\n"
				+ "@@ -0,0 +1 @@\n"
				+ "+folder\n"
				+ "\\ No newline at end of file\n";
		assertEquals(expected.toString(), actual);
