	public void testCreateNewFile() throws IOException {
		File f = new File(trash
		FileUtils.createNewFile(f);
		assertTrue(f.exists());

		try {
			FileUtils.createNewFile(f);
			fail("creation of already existing file must fail");
		} catch (IOException e) {
		}

		FileUtils.delete(f);
	}

