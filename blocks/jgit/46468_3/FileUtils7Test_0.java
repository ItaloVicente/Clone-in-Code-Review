
	@Test
	public void testAtomicMove() throws IOException {
		File src = new File(trash
		Files.createFile(src.toPath());
		File dst = new File(trash
		FileUtils.rename(src
		assertFalse(Files.exists(src.toPath()));
		assertTrue(Files.exists(dst.toPath()));
	}
