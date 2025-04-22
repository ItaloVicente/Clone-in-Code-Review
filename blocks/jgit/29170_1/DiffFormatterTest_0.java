	@Test
	public void testDiffRootNullToTree() throws Exception {
		write(new File(db.getDirectory().getParent()
		File folder = new File(db.getDirectory().getParent()
		FileUtils.mkdir(folder);
		write(new File(folder
		Git git = new Git(db);
		git.add().addFilepattern(".").call();
		RevCommit commit = git.commit().setMessage("Initial commit").call();
		write(new File(folder

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		DiffFormatter df = new DiffFormatter(new SafeBufferedOutputStream(os));
		df.setRepository(db);
		df.setPathFilter(PathFilter.create("folder"));
		df.format(null
		df.flush();

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

	@Test
	public void testDiffRootTreeToNull() throws Exception {
		write(new File(db.getDirectory().getParent()
		File folder = new File(db.getDirectory().getParent()
		FileUtils.mkdir(folder);
		write(new File(folder
		Git git = new Git(db);
		git.add().addFilepattern(".").call();
		RevCommit commit = git.commit().setMessage("Initial commit").call();
		write(new File(folder

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		DiffFormatter df = new DiffFormatter(new SafeBufferedOutputStream(os));
		df.setRepository(db);
		df.setPathFilter(PathFilter.create("folder"));
		df.format(commit.getTree().getId()
		df.flush();

		String actual = os.toString("UTF-8");
		String expected = "diff --git a/folder/folder.txt b/folder/folder.txt\n"
				+ "deleted file mode 100644\n"
				+ "index 0119635..0000000\n"
				+ "--- a/folder/folder.txt\n"
				+ "+++ /dev/null\n"
				+ "@@ -1 +0
				+ "-folder\n"
				+ "\\ No newline at end of file\n";

		assertEquals(expected
	}

	@Test
	public void testDiffNullToNull() throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		DiffFormatter df = new DiffFormatter(new SafeBufferedOutputStream(os));
		df.setRepository(db);
		df.format((AnyObjectId) null
		df.flush();

		String actual = os.toString("UTF-8");
		String expected = "";

		assertEquals(expected
	}

