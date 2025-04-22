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
		String expected =
 "diff --git a/folder/folder.txt b/folder/folder.txt\n"
				+ "index 0119635..95c4c65 100644\n"
				+ "--- a/folder/folder.txt\n" + "+++ b/folder/folder.txt\n"
				+ "@@ -1 +1 @@\n" + "-folder\n"
				+ "\\ No newline at end of file\n" + "+folder change\n"
				+ "\\ No newline at end of file\n";

		assertEquals(expected.toString()
	}

