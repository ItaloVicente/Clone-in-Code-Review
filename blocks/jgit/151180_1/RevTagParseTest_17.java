		try (RevWalk rw = new RevWalk(db)) {
			c.parseCanonical(rw
			assertNotNull(c.getObject());
			assertEquals(id
			assertSame(rw.lookupAny(id
		}
