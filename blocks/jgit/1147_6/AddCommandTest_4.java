		git.add().addFilepattern(".").call();
		modTimes.add(db.getIndexFile().lastModified());
		assertEquals(
				"[sub/a.txt
				"[sub/b.txt
				indexState(CONTENT_ID));
	}

	@SuppressWarnings("boxing")
	public void testAddWithoutParameterUpdate() throws Exception {
		new File(db.getWorkTree()
		File file = new File(db.getWorkTree()
		file.createNewFile();
		PrintWriter writer = new PrintWriter(file);
		writer.print("content");
		writer.close();
		modTimes.add(file.lastModified());

		File file2 = new File(db.getWorkTree()
		file2.createNewFile();
		writer = new PrintWriter(file2);
		writer.print("content b");
		writer.close();
		modTimes.add(file2.lastModified());

		Git git = new Git(db);
		DirCache dc = git.add().addFilepattern("sub").call();

		modTimes.add(db.getIndexFile().lastModified());
		assertEquals(
				"[sub/a.txt
				"[sub/b.txt
				indexState(CONTENT_ID));
		assertTrue(dc.getEntry("sub/a.txt").getLength() == 7);
		assertNotNull(dc.getEntry("sub/b.txt"));

		git.commit().setMessage("commit").call();

		File file3 = new File(db.getWorkTree()
		file3.createNewFile();
		writer = new PrintWriter(file3);
		writer.print("content c");
		writer.close();
		modTimes.add(file2.lastModified());

		writer = new PrintWriter(file);
		writer.print("modified content");
		writer.close();
		modTimes.add(file.lastModified());

		file2.delete();

		dc = git.add().addFilepattern("sub").call();
		modTimes.add(db.getIndexFile().lastModified());
		assertEquals(
				"[sub/a.txt
				"[sub/b.txt
				"[sub/c.txt
				indexState(CONTENT_ID));
	}

	@SuppressWarnings("boxing")
	public void testAddWithParameterUpdate() throws Exception {
		new File(db.getWorkTree()
		File file = new File(db.getWorkTree()
		file.createNewFile();
		PrintWriter writer = new PrintWriter(file);
		writer.print("content");
		writer.close();
		modTimes.add(file.lastModified());

		File file2 = new File(db.getWorkTree()
		file2.createNewFile();
		writer = new PrintWriter(file2);
		writer.print("content b");
		writer.close();
		modTimes.add(file2.lastModified());

		Git git = new Git(db);
		DirCache dc = git.add().addFilepattern("sub").call();
		modTimes.add(db.getIndexFile().lastModified());

		assertEquals(
				"[sub/a.txt
				"[sub/b.txt
				indexState(CONTENT_ID));
		assertTrue(dc.getEntry("sub/a.txt").getLength() == 7);
		assertNotNull(dc.getEntry("sub/b.txt"));

		git.commit().setMessage("commit").call();

		File file3 = new File(db.getWorkTree()
		file3.createNewFile();
		writer = new PrintWriter(file3);
		writer.print("content c");
		writer.close();
		modTimes.add(file3.lastModified());

		writer = new PrintWriter(file);
		writer.print("modified content");
		writer.close();
		modTimes.add(file.lastModified());

		file2.delete();

		dc = git.add().addFilepattern("sub").setUpdate(true).call();
		modTimes.add(db.getIndexFile().lastModified());

		dc = git.add().addFilepattern("sub").setUpdate(true).call();
		assertTrue(dc.getEntry("sub/a.txt").getLastModified() ==
			file.lastModified());
		assertNull(dc.getEntry("sub/c.txt"));
		assertNull(dc.getEntry("sub/b.txt"));
