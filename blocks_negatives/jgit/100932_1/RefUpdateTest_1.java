		assertFalse(ref.isPeeled());
		assertEquals(bad, ref.getObjectId());

		try (RevWalk rw = new RevWalk(db)) {
			rw.parseAny(ref.getObjectId());
			fail("Expected MissingObjectException");
		} catch (MissingObjectException expected) {
			assertEquals(bad, expected.getObjectId());
		}

		RefDirectory refdir = (RefDirectory) db.getRefDatabase();
		try {
			refdir.pack(Arrays.asList(name));
		} catch (MissingObjectException expected) {
			assertEquals(bad, expected.getObjectId());
		}
