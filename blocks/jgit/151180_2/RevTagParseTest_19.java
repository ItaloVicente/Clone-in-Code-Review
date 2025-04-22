		try (RevWalk rw = new RevWalk(db)) {
			c.parseCanonical(rw
			assertNotNull(c.getObject());
			assertEquals(treeId
			assertSame(rw.lookupTree(treeId)
		}
