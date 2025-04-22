	@Test
	public void testDeleteSymlinkToDirectoryDoesNotDeleteTarget()
			throws IOException {
		org.junit.Assume.assumeTrue(FS.DETECTED.supportsSymlinks());
		FS fs = FS.DETECTED;
		File dir = new File(trash
		File file = new File(dir
		File link = new File(trash
		FileUtils.mkdirs(dir);
		FileUtils.createNewFile(file);
		fs.createSymLink(link
		FileUtils.delete(link
		assertFalse(link.exists());
		assertTrue(dir.exists());
		assertTrue(file.exists());
	}

	@Test
	public void testAtomicMove() throws IOException {
		File src = new File(trash
		Files.createFile(src.toPath());
		File dst = new File(trash
		FileUtils.rename(src
		assertFalse(Files.exists(src.toPath()));
		assertTrue(Files.exists(dst.toPath()));
	}

