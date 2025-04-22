		git.add().addFilepattern(".").call();
		assertEquals(
				"[sub/a.txt
				"[sub/b.txt
				indexState(CONTENT_ID));
	}

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
		git.add().addFilepattern("sub").call();

		assertEquals(
				"[sub/a.txt
				"[sub/b.txt
				indexState(CONTENT_ID));

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

		git.add().addFilepattern("sub").call();
		assertEquals(
				"[sub/a.txt
				"[sub/b.txt
				"[sub/c.txt
				indexState(CONTENT_ID));
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
		git.add().addFilepattern("sub").call();

		assertEquals(
				"[sub/a.txt
				"[sub/b.txt
				indexState(CONTENT_ID));

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

		git.add().addFilepattern("sub").setUpdate(true).call();
		assertEquals(
				"[sub/a.txt
				indexState(CONTENT_ID));
