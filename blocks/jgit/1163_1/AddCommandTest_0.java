	public void testAddIgnoredFile() throws Exception  {
		new File(db.getWorkDir()
		File file = new File(db.getWorkDir()
		file.createNewFile();
		PrintWriter writer = new PrintWriter(file);
		writer.print("content");
		writer.close();

		File ignoreFile = new File(db.getWorkDir()
		ignoreFile.createNewFile();
		writer = new PrintWriter(ignoreFile);
		writer.print("sub/b.txt");
		writer.close();

		File file2 = new File(db.getWorkDir()
		file2.createNewFile();
		writer = new PrintWriter(file2);
		writer.print("content b");
		writer.close();

		Git git = new Git(db);
		DirCache dc = git.add().addFilepattern("sub").call();
		assertEquals("sub/a.txt"
		assertNull(dc.getEntry("sub/b.txt"));
		assertNotNull(dc.getEntry("sub/a.txt").getObjectId());
		assertEquals(0
	}

