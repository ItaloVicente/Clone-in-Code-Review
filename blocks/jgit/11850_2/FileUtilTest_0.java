	@Test

	public void testDeleteRecursiveEmpty() throws IOException {
		File f1 = new File(trash
		File f2 = new File(trash
		File d1 = new File(trash
		File d2 = new File(trash
		File d3 = new File(trash
		FileUtils.mkdirs(f1.getParentFile());
		FileUtils.createNewFile(f2);
		FileUtils.createNewFile(f1);
		FileUtils.mkdirs(d3);

		try {
			FileUtils.delete(d1
			fail("delete should fail");
		} catch (IOException e1) {
			try {
				FileUtils.delete(d1
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
			FileUtils.delete(d1
			fail("delete should fail");
		} catch (IOException e2) {
			assertTrue(d1.exists());
			assertTrue(d2.exists());
			assertTrue(d3.exists());
		}

		FileUtils.delete(d2
				| FileUtils.RECURSIVE);
		assertFalse(d2.exists());

		try {
			FileUtils.delete(d2
			fail("Cannot delete non-existent entity");
		} catch (IOException e) {
		}

		FileUtils.delete(d2
				| FileUtils.SKIP_MISSING);
		FileUtils.delete(d2
				| FileUtils.RECURSIVE | FileUtils.SKIP_MISSING);

		FileUtils.delete(d2
				| FileUtils.IGNORE_ERRORS);
		FileUtils.delete(d2
				| FileUtils.RECURSIVE | FileUtils.IGNORE_ERRORS);
	}

	@Test
	public void testDeleteRecursiveEmptyNeedsToCheckFilesFirst()
			throws IOException {
		File d1 = new File(trash
		File d2 = new File(trash
		File d3 = new File(trash
		File f1 = new File(trash
		File d4 = new File(trash
		FileUtils.mkdirs(d1);
		FileUtils.mkdirs(d2);
		FileUtils.mkdirs(d3);
		FileUtils.mkdirs(d4);
		FileUtils.createNewFile(f1);

		try {
			FileUtils.delete(d1
					| FileUtils.RECURSIVE);
			fail("delete should fail");
		} catch (IOException e) {
			assertTrue(f1.exists());
			assertTrue(d1.exists());
			assertTrue(d2.exists());
			assertTrue(d3.exists());
			assertTrue(d4.exists());
		}
	}

	@Test
	public void testDeleteRecursiveEmptyDirectoriesOnlyButIsFile()
			throws IOException {
		File f1 = new File(trash
		FileUtils.mkdirs(f1.getParentFile());
		FileUtils.createNewFile(f1);
		try {
			FileUtils.delete(f1
			fail("delete should fail");
		} catch (IOException e) {
			assertTrue(f1.exists());
		}
	}

