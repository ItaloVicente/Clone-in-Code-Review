		if (autoStageMoves) {
			assertNull(dirCache.getEntry("folder/file.txt"));
			assertNotNull(dirCache.getEntry("dir/file.txt"));
			assertEquals(oldContentId,
					dirCache.getEntry("dir/file.txt").getObjectId());
		} else {
			assertNotNull(dirCache.getEntry("folder/file.txt"));
			assertNull(dirCache.getEntry("dir/file.txt"));
		}
