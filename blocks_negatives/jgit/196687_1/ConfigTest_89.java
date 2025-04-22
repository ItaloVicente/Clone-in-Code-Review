		assertEquals("Core section size", 3, names.size());
		assertTrue("Core section should contain \"filemode\"",
				names.contains("filemode"));
		assertTrue("Core section should contain \"repositoryFormatVersion\"",
				names.contains("repositoryFormatVersion"));
		assertTrue("Core section should contain \"logAllRefUpdates\"",
				names.contains("logAllRefUpdates"));
		assertTrue("Core section should contain \"logallrefupdates\"",
				names.contains("logallrefupdates"));
