			assertTrue(tw.next());
			assertEquals("sub-c/empty", tw.getPathString());
			assertEquals(FileMode.REGULAR_FILE, tw.getFileMode(0));
			assertEquals(FileMode.REGULAR_FILE, tw.getFileMode(1));
			assertEquals(cFileId1, tw.getObjectId(0));
			assertEquals(cFileId2, tw.getObjectId(1));
