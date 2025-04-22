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

