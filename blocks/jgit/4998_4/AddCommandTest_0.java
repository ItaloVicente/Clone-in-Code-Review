	@Test
	public void testAddExistingSingleMediumSizeFileWithNewLine()
			throws IOException
		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		StringBuilder data = new StringBuilder();
		for (int i = 0; i < 1000; ++i) {
			data.append("row1\r\nrow2");
		}
		String crData = data.toString();
		PrintWriter writer = new PrintWriter(file);
		writer.print(crData);
		writer.close();
		String lfData = data.toString().replaceAll("\r"
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

