		if (autoStageDelete) {
			assertEquals(1, dirCache.getEntryCount());
			assertNull(dirCache.getEntry("file.txt"));
		} else {
			assertEquals(2, dirCache.getEntryCount());
			assertNotNull(dirCache.getEntry("file.txt"));
		}
