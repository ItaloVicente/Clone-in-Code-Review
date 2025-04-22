
	@Test
	public void testRenameOverNonExistingFile() throws IOException {
		File d = new File(trash
		FileUtils.mkdirs(d);
		File f1 = new File(trash
		File f2 = new File(trash
		JGitTestUtil.write(f1
		FileUtils.rename(f1
		assertFalse(f1.exists());
		assertTrue(f2.exists());
		assertEquals("f1"
	}

	@Test
	public void testRenameOverExistingFile() throws IOException {
		File d = new File(trash
		FileUtils.mkdirs(d);
		File f1 = new File(trash
		File f2 = new File(trash
		JGitTestUtil.write(f1
		JGitTestUtil.write(f2
		FileUtils.rename(f1
		assertFalse(f1.exists());
		assertTrue(f2.exists());
		assertEquals("f1"
	}

	@Test
	public void testRenameOverExistingNonEmptyDirectory() throws IOException {
		File d = new File(trash
		FileUtils.mkdirs(d);
		File f1 = new File(trash
		File f2 = new File(trash
		File d1 = new File(trash
		File f3 = new File(trash
		FileUtils.mkdirs(d1);
		JGitTestUtil.write(f1
		JGitTestUtil.write(f3
		try {
			FileUtils.rename(f1
			fail("rename to non-empty directory should fail");
		} catch (IOException e) {
			assertEquals("f1"
			assertEquals("f3"
		}
	}

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
