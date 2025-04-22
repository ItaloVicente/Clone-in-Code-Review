	@Test
	public void testAddExistingSingleMediumSizeFileWithNewLine()
			throws IOException, NoFilepatternException {
		File file = new File(db.getWorkTree(), "a.txt");
		FileUtils.createNewFile(file);
		StringBuilder data = new StringBuilder();
		for (int i = 0; i < 1000; ++i) {
			data.append("row1\r\nrow2");
		}
		String crData = data.toString();
		PrintWriter writer = new PrintWriter(file);
		writer.print(crData);
		writer.close();
		String lfData = data.toString().replaceAll("\r", "");
		Git git = new Git(db);
		db.getConfig().setString("core", null, "autocrlf", "false");
		git.add().addFilepattern("a.txt").call();
		assertEquals("[a.txt, mode:100644, content:" + data + "]",
				indexState(CONTENT));
		db.getConfig().setString("core", null, "autocrlf", "true");
		git.add().addFilepattern("a.txt").call();
		assertEquals("[a.txt, mode:100644, content:" + lfData + "]",
				indexState(CONTENT));
		db.getConfig().setString("core", null, "autocrlf", "input");
		git.add().addFilepattern("a.txt").call();
		assertEquals("[a.txt, mode:100644, content:" + lfData + "]",
				indexState(CONTENT));
	}

