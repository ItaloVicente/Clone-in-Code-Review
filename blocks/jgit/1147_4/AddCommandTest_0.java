	public void testAddWithoutParameterUpdate() throws Exception {
		new File(db.getWorkTree()
		File file = new File(db.getWorkTree()
		file.createNewFile();
		PrintWriter writer = new PrintWriter(file);
		writer.print("content");
		writer.close();

		File file2 = new File(db.getWorkTree()
		file2.createNewFile();
		writer = new PrintWriter(file2);
		writer.print("content b");
		writer.close();

		Git git = new Git(db);
		DirCache dc = git.add().addFilepattern("sub").call();

		assertTrue(dc.getEntry("sub/a.txt").getLength() == 7);
		assertNotNull(dc.getEntry("sub/b.txt"));

		git.commit().setMessage("commit").call();

		File file3 = new File(db.getWorkTree()
		file3.createNewFile();
		writer = new PrintWriter(file3);
		writer.print("content c");
		writer.close();

		writer = new PrintWriter(file);
		writer.print("modified content");
		writer.close();

		file2.delete();

		dc = git.add().addFilepattern("sub").call();
		assertTrue(dc.getEntry("sub/a.txt").getLastModified() ==
			file.lastModified());
		assertNotNull(dc.getEntry("sub/c.txt"));
		assertNotNull(dc.getEntry("sub/b.txt"));
	}

	public void testAddWithParameterUpdate() throws Exception {
		new File(db.getWorkTree()
		File file = new File(db.getWorkTree()
		file.createNewFile();
		PrintWriter writer = new PrintWriter(file);
		writer.print("content");
		writer.close();

		File file2 = new File(db.getWorkTree()
		file2.createNewFile();
		writer = new PrintWriter(file2);
		writer.print("content b");
		writer.close();

		Git git = new Git(db);
		DirCache dc = git.add().addFilepattern("sub").call();

		assertTrue(dc.getEntry("sub/a.txt").getLength() == 7);
		assertNotNull(dc.getEntry("sub/b.txt"));

		git.commit().setMessage("commit").call();

		File file3 = new File(db.getWorkTree()
		file3.createNewFile();
		writer = new PrintWriter(file3);
		writer.print("content c");
		writer.close();

		writer = new PrintWriter(file);
		writer.print("modified content");
		writer.close();

		file2.delete();

		dc = git.add().addFilepattern("sub").setUpdate(true).call();
		assertTrue(dc.getEntry("sub/a.txt").getLastModified() ==
			file.lastModified());
		assertNull(dc.getEntry("sub/c.txt"));
		assertNull(dc.getEntry("sub/b.txt"));
	}

