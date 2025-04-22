		try (RevWalk rw = new RevWalk(db)) {
			c.parseCanonical(rw
			assertNotNull(c.getTree());
			assertEquals(treeId
			assertSame(rw.lookupTree(treeId)
		}
