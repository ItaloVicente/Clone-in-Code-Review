		if (autoStageMoves) {
			assertNotNull(dirCache.getEntry("data.txt"));
			assertEquals(oldContentId,
					dirCache.getEntry("data.txt").getObjectId());
		} else {
			assertNull(dirCache.getEntry("data.txt"));
		}
