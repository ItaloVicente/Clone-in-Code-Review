		assertEquals(tableDir.listFiles().length
	}

	@Test
	public void testOpenConvert() throws Exception {
		FileRepository repo = new FileRepository(db.getDirectory());
		assertTrue(repo.getRefDatabase() instanceof FileReftableDatabase);
