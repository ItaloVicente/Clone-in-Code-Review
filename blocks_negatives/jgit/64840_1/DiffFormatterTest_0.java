		Git git = new Git(db);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("Initial commit").call();
		write(new File(folder, "folder.txt"), "folder change");

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		DiffFormatter dfmt = new DiffFormatter(new SafeBufferedOutputStream(os));
		dfmt.setRepository(db);
		dfmt.setPathFilter(PathFilter.create("folder"));
		DirCacheIterator oldTree = new DirCacheIterator(db.readDirCache());
		FileTreeIterator newTree = new FileTreeIterator(db);
		dfmt.format(oldTree, newTree);
		dfmt.flush();

		String actual = os.toString("UTF-8");
		String expected =
 "diff --git a/folder/folder.txt b/folder/folder.txt\n"
				+ "index 0119635..95c4c65 100644\n"
				+ "--- a/folder/folder.txt\n" + "+++ b/folder/folder.txt\n"
				+ "@@ -1 +1 @@\n" + "-folder\n"
				+ "\\ No newline at end of file\n" + "+folder change\n"
				+ "\\ No newline at end of file\n";

		assertEquals(expected, actual);
