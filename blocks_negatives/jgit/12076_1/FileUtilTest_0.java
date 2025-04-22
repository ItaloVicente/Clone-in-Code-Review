	@Test
	public void testDeleteRecursiveEmpty() throws IOException {
		File f1 = new File(trash, "test/test/a");
		File f2 = new File(trash, "test/a");
		File d1 = new File(trash, "test");
		File d2 = new File(trash, "test/test");
		File d3 = new File(trash, "test/b");
		FileUtils.mkdirs(f1.getParentFile());
		FileUtils.createNewFile(f2);
		FileUtils.createNewFile(f1);
		FileUtils.mkdirs(d3);

		try {
			FileUtils.delete(d1, FileUtils.EMPTY_DIRECTORIES_ONLY);
			fail("delete should fail");
		} catch (IOException e1) {
			try {
				FileUtils.delete(d1, FileUtils.EMPTY_DIRECTORIES_ONLY|FileUtils.RECURSIVE);
				fail("delete should fail");
			} catch (IOException e2) {
				assertTrue(f1.exists());
				assertTrue(f2.exists());
				assertTrue(d1.exists());
				assertTrue(d2.exists());
				assertTrue(d3.exists());
			}
		}

		assertTrue(f1.delete());
		assertTrue(f2.delete());

		try {
			FileUtils.delete(d1, FileUtils.EMPTY_DIRECTORIES_ONLY);
			fail("delete should fail");
		} catch (IOException e2) {
			assertTrue(d1.exists());
			assertTrue(d2.exists());
			assertTrue(d3.exists());
		}

		FileUtils.delete(d2, FileUtils.EMPTY_DIRECTORIES_ONLY
				| FileUtils.RECURSIVE);
		assertFalse(d2.exists());

		try {
			FileUtils.delete(d2, FileUtils.EMPTY_DIRECTORIES_ONLY);
			fail("Cannot delete non-existent entity");
		} catch (IOException e) {
		}

		FileUtils.delete(d2, FileUtils.EMPTY_DIRECTORIES_ONLY
				| FileUtils.SKIP_MISSING);
		FileUtils.delete(d2, FileUtils.EMPTY_DIRECTORIES_ONLY
				| FileUtils.RECURSIVE | FileUtils.SKIP_MISSING);

		FileUtils.delete(d2, FileUtils.EMPTY_DIRECTORIES_ONLY
				| FileUtils.IGNORE_ERRORS);
		FileUtils.delete(d2, FileUtils.EMPTY_DIRECTORIES_ONLY
				| FileUtils.RECURSIVE | FileUtils.IGNORE_ERRORS);
	}

	@Test
	public void testDeleteRecursiveEmptyDirectoriesOnlyButIsFile()
			throws IOException {
		File f1 = new File(trash, "test/test/a");
		FileUtils.mkdirs(f1.getParentFile());
		FileUtils.createNewFile(f1);
		try {
			FileUtils.delete(f1, FileUtils.EMPTY_DIRECTORIES_ONLY);
			fail("delete should fail");
		} catch (IOException e) {
			assertTrue(f1.exists());
		}
	}

