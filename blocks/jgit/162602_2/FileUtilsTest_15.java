	@Test
	public void testDeleteNonRecursiveTreeNotOk() throws IOException {
		File t = new File(trash
		FileUtils.mkdir(t);
		File f = new File(t
		FileUtils.createNewFile(f);
		try {
			FileUtils.delete(t
			fail("expected failure to delete f");
		} catch (IOException e) {
			assertTrue(e.getMessage().endsWith(t.getAbsolutePath()));
		}
		assertTrue(f.exists());
		assertTrue(t.exists());
	}

	@Test
	public void testDeleteNonRecursiveTreeIgnoreError() throws IOException {
		File t = new File(trash
		FileUtils.mkdir(t);
		File f = new File(t
		FileUtils.createNewFile(f);
		FileUtils.delete(t
				FileUtils.EMPTY_DIRECTORIES_ONLY | FileUtils.IGNORE_ERRORS);
		assertTrue(f.exists());
		assertTrue(t.exists());
	}

