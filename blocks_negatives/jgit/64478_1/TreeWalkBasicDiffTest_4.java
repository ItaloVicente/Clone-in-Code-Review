			assertTrue(tw.next());
			assertEquals("sub-b/empty", tw.getPathString());
			assertEquals(FileMode.MISSING, tw.getFileMode(0));
			assertEquals(FileMode.REGULAR_FILE, tw.getFileMode(1));
			assertEquals(ObjectId.zeroId(), tw.getObjectId(0));
			assertEquals(bFileId, tw.getObjectId(1));
