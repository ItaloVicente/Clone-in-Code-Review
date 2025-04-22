		try {
			db.resolve(abbrev8.name());
			fail("did not throw AmbiguousObjectException");
		} catch (AmbiguousObjectException err) {
			assertEquals(abbrev8
			matches = err.getCandidates();
			assertNotNull(matches);
			assertEquals(objects.size()
			for (PackedObjectInfo info : objects)
				assertTrue("contains " + info.name()
		}

