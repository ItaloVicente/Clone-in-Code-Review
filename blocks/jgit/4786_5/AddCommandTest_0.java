	@Test
	public void testAddExistingSingleFileWithNewLine() throws IOException
			NoFilepatternException {
		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		PrintWriter writer = new PrintWriter(file);
		writer.print("row1\r\nrow2");
		writer.close();

		Git git = new Git(db);
		db.getConfig().setString("core"
		git.add().addFilepattern("a.txt").call();
		assertEquals("[a.txt
				indexState(CONTENT));
		db.getConfig().setString("core"
		git.add().addFilepattern("a.txt").call();
		assertEquals("[a.txt
				indexState(CONTENT));
		db.getConfig().setString("core"
		git.add().addFilepattern("a.txt").call();
		assertEquals("[a.txt
				indexState(CONTENT));
	}

	@Test
	public void testAddExistingSingleBinaryFile() throws IOException
			NoFilepatternException {
		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		PrintWriter writer = new PrintWriter(file);
		writer.print("row1\r\nrow2\u0000");
		writer.close();

		Git git = new Git(db);
		db.getConfig().setString("core"
		git.add().addFilepattern("a.txt").call();
		assertEquals("[a.txt
				indexState(CONTENT));
		db.getConfig().setString("core"
		git.add().addFilepattern("a.txt").call();
		assertEquals("[a.txt
				indexState(CONTENT));
		db.getConfig().setString("core"
		git.add().addFilepattern("a.txt").call();
		assertEquals("[a.txt
				indexState(CONTENT));
	}

