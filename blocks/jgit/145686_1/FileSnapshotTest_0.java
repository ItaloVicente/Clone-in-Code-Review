	@Test
	public void fileSnapshotEquals() throws Exception {
		FileSnapshot fs1 = FileSnapshot.MISSING_FILE;
		FileSnapshot fs2 = FileSnapshot.save(fs1.lastModified());

		assertTrue(fs1.equals(fs2));
		assertTrue(fs2.equals(fs1));
	}

