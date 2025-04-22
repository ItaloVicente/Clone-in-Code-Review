	@Test
	public void testRenameOverExistingEmptyDirectory() throws IOException {
		File d = new File(trash
		FileUtils.mkdirs(d);
		File f1 = new File(trash
		File f2 = new File(trash
		File d1 = new File(trash
		FileUtils.mkdirs(d1);
		JGitTestUtil.write(f1
		FileUtils.rename(f1
		assertFalse(f1.exists());
		assertTrue(f2.exists());
		assertEquals("f1"
	}
