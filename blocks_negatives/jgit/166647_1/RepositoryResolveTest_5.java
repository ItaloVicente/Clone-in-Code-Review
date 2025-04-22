		assertEquals(id, db.resolve("refs/heads/foo-g032c"));
		assertEquals(id, db.resolve("foo-g032c"));
		assertNull(db.resolve("foo-g032"));
		assertNull(db.resolve("foo-g03"));
		assertNull(db.resolve("foo-g0"));
		assertNull(db.resolve("foo-g"));
