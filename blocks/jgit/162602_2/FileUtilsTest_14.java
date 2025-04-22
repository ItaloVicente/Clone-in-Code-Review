	@Test
	public void testDeleteReadOnlyFile() throws IOException {
		File f = new File(trash
		FileUtils.createNewFile(f);
		assertTrue(f.setReadOnly());
		FileUtils.delete(f);
		assertFalse(f.exists());
	}

