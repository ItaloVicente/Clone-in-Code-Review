		assertEquals(1, dc.getEntryCount());
		assertEquals("sub/a.txt", dc.getEntry(0).getPathString());
		assertNotNull(dc.getEntry(0).getObjectId());
		assertEquals(file.lastModified(), dc.getEntry(0).getLastModified());
		assertEquals(file.length(), dc.getEntry(0).getLength());
		assertEquals(FileMode.REGULAR_FILE, dc.getEntry(0).getFileMode());
		assertEquals(0, dc.getEntry(0).getStage());
