		DirCache dc = git.add().addFilepattern("a.txt").addFilepattern("b.txt").call();
		assertEquals("a.txt", dc.getEntry("a.txt").getPathString());
		assertEquals("b.txt", dc.getEntry("b.txt").getPathString());
		assertNotNull(dc.getEntry("a.txt").getObjectId());
		assertNotNull(dc.getEntry("b.txt").getObjectId());
		assertEquals(0, dc.getEntry("a.txt").getStage());
		assertEquals(0, dc.getEntry("b.txt").getStage());
