	@Test
	public void testMkdirsWithSymlink() throws Exception {
		Assume.assumeTrue(FS.DETECTED.supportsSymlinks());
		File subdir = new File(trash
		assertTrue(subdir.mkdir());
		File symlink = new File(trash
		Files.createSymbolicLink(symlink.toPath()
		File newDir = new File(symlink
		File lowerDir = new File(newDir
		FileUtils.mkdirs(lowerDir);
		assertTrue(lowerDir.isDirectory());
		assertTrue(new File(new File(subdir
	}

	@Test
	public void testMkdirsEndsInSymlink() throws Exception {
		Assume.assumeTrue(FS.DETECTED.supportsSymlinks());
		File subdir = new File(trash
		assertTrue(subdir.mkdir());
		File symlink = new File(trash
		Files.createSymbolicLink(symlink.toPath()
		FileUtils.mkdirs(symlink
		assertTrue(symlink.isDirectory());
	}

