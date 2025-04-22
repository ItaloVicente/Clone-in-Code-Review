		if (autoStageMoves) {
			assertNotNull(dirCache.getEntry(gdRelativeDstParent + "file.txt"));
			assertEquals(oldContentId, dirCache
					.getEntry(gdRelativeDstParent + "file.txt").getObjectId());
		} else {
			assertNull(dirCache.getEntry(gdRelativeDstParent + "file.txt"));
		}
