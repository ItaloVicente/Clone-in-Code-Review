
		assertTrue("Core section should contain \"repositoryFormatVersion\""
				names.contains("repositoryFormatVersion"));

		assertTrue("Core section should contain \"repositoryformatversion\""
				names.contains("repositoryformatversion"));

		Iterator<String> itr = names.iterator();
		assertEquals("repositoryFormatVersion"
		assertEquals("filemode"
		assertEquals("logAllRefUpdates"
		assertFalse(itr.hasNext());
