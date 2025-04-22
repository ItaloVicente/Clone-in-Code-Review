	@Test
	public void testCheckoutForced_deleteFileAndRestore() throws Exception {
		File testFile = new File(db.getWorkTree()
		assertTrue(testFile.exists());

		assertEquals("test"
		FileUtils.delete(testFile);
		assertFalse(testFile.exists());
		assertEquals(initialCommit.getId()
			.setForced(true).call().getObjectId());
		assertTrue(testFile.exists());

		assertEquals("master"
		FileUtils.delete(testFile);
		assertFalse(testFile.exists());
		assertEquals(initialCommit.getId()
				.setForced(true).call().getObjectId());
		assertTrue(testFile.exists());
	}

