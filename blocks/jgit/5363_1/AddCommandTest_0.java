	@Test
	public void testAddExistingSingleBigSizeFile() throws IOException
			NoFilepatternException {
		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		PrintWriter writer = new PrintWriter(file);
		for (int i = 0; i < 50000; ++i) {
			writer.print("01234567890123456789012345678901234567890123456789\n");
		}
		writer.close();

		Git git = new Git(db);
		git.add().addFilepattern("a.txt").call();

		assertEquals("[a.txt
				indexState(LENGTH));
	}

