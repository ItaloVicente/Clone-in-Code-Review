		try (Git git = new Git(db);
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				DiffFormatter dfmt = new DiffFormatter(new SafeBufferedOutputStream(os))) {
			git.add().addFilepattern(".").call();
			RevCommit commit = git.commit().setMessage("Initial commit").call();
			write(new File(folder

			dfmt.setRepository(db);
			dfmt.setPathFilter(PathFilter.create("folder"));
			dfmt.format(null
			dfmt.flush();

			String actual = os.toString("UTF-8");
			String expected = "diff --git a/folder/folder.txt b/folder/folder.txt\n"
					+ "new file mode 100644\n"
					+ "index 0000000..0119635\n"
					+ "--- /dev/null\n"
					+ "+++ b/folder/folder.txt\n"
					+ "@@ -0
					+ "+folder\n"
					+ "\\ No newline at end of file\n";
	
			assertEquals(expected
		}
