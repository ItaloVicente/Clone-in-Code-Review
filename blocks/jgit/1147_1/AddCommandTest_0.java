	public void testAddWithParameterUpdate() throws Exception {
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
		DirCache dc = git.add().addFilepattern("sub").call();

		assertTrue(dc.getEntry("sub/a.txt").getLength() == 7);
		assertNull(dc.getEntry("sub/c.txt"));
		assertNotNull(dc.getEntry("sub/b.txt"));

		git.commit().setMessage("commit").call();

		File file3 = new File(db.getWorkDir()
		file3.createNewFile();
		writer = new PrintWriter(file3);
		writer.print("content c");
		writer.close();

		writer = new PrintWriter(file);
		writer.print("modified content");
		writer.close();

		file2.delete();

		dc = git.add().addFilepattern("sub").setUpdate(true).call();
		assertTrue(dc.getEntry("sub/a.txt").getLength() == 16);
		assertNull(dc.getEntry("sub/c.txt"));
		assertNull(dc.getEntry("sub/b.txt"));
	}

