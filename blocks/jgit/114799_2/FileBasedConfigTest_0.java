	@Test
	public void testPreserveSymlink()
			throws IOException
		assumeTrue(!SystemReader.getInstance().isWindows());

		final File file = createFile(CONTENT1.getBytes());
		final File link = new File(file.getAbsolutePath() + ".link");
		FileUtils.createSymLink(link

		assertTrue(!Files.isSymbolicLink(file.toPath()));
		assertTrue(Files.isSymbolicLink(link.toPath()));

		FileBasedConfig config = new FileBasedConfig(link
		config.load();
		assertEquals(ALICE

		config.setString(USER
		config.save();

		config = new FileBasedConfig(link
		config.load();
		assertEquals(BOB

		assertTrue(!Files.isSymbolicLink(file.toPath()));
		assertTrue(Files.isSymbolicLink(link.toPath()));
	}

