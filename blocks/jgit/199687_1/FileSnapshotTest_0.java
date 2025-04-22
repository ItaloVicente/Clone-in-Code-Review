	@Test
	public void snapshotAndFileMissingIsNotModified() throws Exception {
		File doesNotExist = trash.resolve("DOES_NOT_EXIST").toFile();
		FileSnapshot missing = FileSnapshot.save(doesNotExist);
		assertFalse(missing.isModified(doesNotExist));
	}

	@Test
	public void missingFileEquals() throws Exception {
		FileSnapshot missing = FileSnapshot.save(
				trash.resolve("DOES_NOT_EXIST").toFile());
		assertTrue(missing.equals(FileSnapshot.MISSING_FILE));
	}

