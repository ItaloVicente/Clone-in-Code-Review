	public void testAddWholeRepo() throws Exception  {
		new File(db.getWorkDir()
		File file = new File(db.getWorkDir()
		file.createNewFile();
		PrintWriter writer = new PrintWriter(file);
		writer.print("content");
		writer.close();

		File file2 = new File(db.getWorkDir()
		file2.createNewFile();
		writer = new PrintWriter(file2);
		writer.print("content b");
		writer.close();

		Git git = new Git(db);
		DirCache dc = git.add().addFilepattern(".").call();
		assertEquals("sub/a.txt"
		assertEquals("sub/b.txt"
	}

