	public void testAddExistingSingleMixedLineEndings() throws Exception {
		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		PrintWriter writer = new PrintWriter(file);
		writer.print("row1\r\nrow2\n");
		writer.close();

		Git git = new Git(db);
		db.getConfig().setString("core"
		db.getConfig().setString("core"
		git.add().addFilepattern("a.txt").call();
		assertEquals("[a.txt
				indexState(CONTENT));
		db.getConfig().setString("core"
		try {
			git.add().addFilepattern("a.txt").call();
			fail("Expected UnsafeCRLFConversion exception");
		} catch (GitAPIException e) {
			assertSame(UnsafeCRLFConversionException.class
					.getClass());
			e.getCause().getMessage().endsWith("/a.txt");
		}
		db.getConfig().setString("core"
		try {
			git.add().addFilepattern("a.txt").call();
			fail("Expected UnsafeCRLFConversion exception");
		} catch (GitAPIException e) {
			assertSame(UnsafeCRLFConversionException.class
					.getClass());
			e.getCause().getMessage().endsWith("/a.txt");
		}
	}

	@Test
	public void testAddExistingSingleFileInSubDir() throws IOException
			NoFilepatternException
