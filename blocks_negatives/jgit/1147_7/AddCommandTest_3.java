		DirCache dc = git.add().addFilepattern("sub").call();
		assertEquals("sub/a.txt", dc.getEntry("sub/a.txt").getPathString());
		assertEquals("sub/b.txt", dc.getEntry("sub/b.txt").getPathString());
		assertNotNull(dc.getEntry("sub/a.txt").getObjectId());
		assertNotNull(dc.getEntry("sub/b.txt").getObjectId());
		assertEquals(0, dc.getEntry("sub/a.txt").getStage());
		assertEquals(0, dc.getEntry("sub/b.txt").getStage());
