	@Test
	public void testDeleteEmptyTreeOk() throws IOException {
		File t = new File(trash
		FileUtils.mkdir(t);
		FileUtils.mkdir(new File(t
		FileUtils.mkdir(new File(new File(t
		FileUtils.delete(t
		assertFalse(t.exists());
	}

	@Test
	public void testDeleteNotEmptyTreeNotOk() throws IOException {
		File t = new File(trash
		FileUtils.mkdir(t);
		FileUtils.mkdir(new File(t
		File f = new File(new File(t
		FileUtils.createNewFile(f);
		FileUtils.mkdir(new File(new File(t
		try {
			FileUtils.delete(t
			fail("expected failure to delete f");
		} catch (IOException e) {
			assertThat(e.getMessage()
		}
		assertTrue(t.exists());
	}

	@Test
	public void testDeleteNotEmptyTreeNotOkButIgnoreFail() throws IOException {
		File t = new File(trash
		FileUtils.mkdir(t);
		FileUtils.mkdir(new File(t
		File f = new File(new File(t
		FileUtils.createNewFile(f);
		File e = new File(new File(t
		FileUtils.mkdir(e);
		FileUtils.delete(t
				| FileUtils.IGNORE_ERRORS);
		assertTrue(t.exists());
		assertTrue(f.exists());
		assertFalse(e.exists());
	}
