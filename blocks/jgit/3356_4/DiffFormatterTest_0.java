	@Test
	public void testDiff() throws Exception {
		write(new File(db.getDirectory().getParent()
		File folder = new File(db.getDirectory().getParent()
		folder.mkdir();
		write(new File(folder
		Git git = new Git(db);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("Initial commit").call();
		write(new File(folder

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		DiffFormatter df = new DiffFormatter(new BufferedOutputStream(os));
		df.setRepository(db);
		df.setPathFilter(PathFilter.create("folder"));
		DirCacheIterator oldTree = new DirCacheIterator(db.readDirCache());
		FileTreeIterator newTree = new FileTreeIterator(db);
		df.format(oldTree
		df.flush();

		String actual = os.toString();
		StringBuilder expected = new StringBuilder();
		expected.append("diff --git a/folder/folder.txt b/folder/folder.txt")
				.append("\n");
		expected.append("index 0119635..95c4c65 100644").append("\n");
		expected.append("--- a/folder/folder.txt").append("\n");
		expected.append("+++ b/folder/folder.txt").append("\n");
		expected.append("@@ -1 +1 @@").append("\n");
		expected.append("-folder").append("\n");
		expected.append("\\ No newline at end of file").append("\n");
		expected.append("+folder change").append("\n");
		expected.append("\\ No newline at end of file").append("\n");

		assertEquals(expected.toString()
	}

