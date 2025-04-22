		Git git = new Git(db);
		git.add().addFilepattern(".").call();
		RevCommit commit = git.commit().setMessage("Initial commit").call();
		write(new File(folder, "folder.txt"), "folder change");

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		DiffFormatter dfmt = new DiffFormatter(new SafeBufferedOutputStream(os));
		dfmt.setRepository(db);
		dfmt.setPathFilter(PathFilter.create("folder"));
		dfmt.format(commit.getTree().getId(), null);
		dfmt.flush();

		String actual = os.toString("UTF-8");
		String expected = "diff --git a/folder/folder.txt b/folder/folder.txt\n"
				+ "deleted file mode 100644\n"
				+ "index 0119635..0000000\n"
				+ "--- a/folder/folder.txt\n"
				+ "+++ /dev/null\n"
				+ "@@ -1 +0,0 @@\n"
				+ "-folder\n"
				+ "\\ No newline at end of file\n";

		assertEquals(expected, actual);
