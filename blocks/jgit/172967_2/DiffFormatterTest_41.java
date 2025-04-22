	@Test
	public void testDefaultRenameDetectorSettings() throws Exception {
		RenameDetector rd = df.getRenameDetector();
		assertNull(rd);
		df.setDetectRenames(true);
		rd = df.getRenameDetector();
		assertNotNull(rd);
		assertEquals(400
		assertEquals(60
	}

