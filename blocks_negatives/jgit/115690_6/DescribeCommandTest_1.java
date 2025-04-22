		assertEquals("bob-t2", describe(c3));
		assertEquals("bob-t2-0-g44579eb", describe(c3, true));
		assertEquals("alice-t1-1-g44579eb", describe(c3, "alice*"));
		assertEquals("alice-t1-1-g44579eb", describe(c3, "a??c?-t*"));
		assertEquals("bob-t2", describe(c3, "bob*"));
		assertEquals("bob-t2", describe(c3, "?ob*"));
		assertEquals("bob-t2", describe(c3, "a*", "b*", "c*"));

		assertNameStartsWith(c4, "3e563c5");
		assertEquals("bob-t2-1-g3e563c5", describe(c4));
		assertEquals("bob-t2-1-g3e563c5", describe(c4, true));
		assertEquals("alice-t1-2-g3e563c5", describe(c4, "alice*"));
		assertEquals("bob-t2-1-g3e563c5", describe(c4, "bob*"));
		assertEquals("bob-t2-1-g3e563c5", describe(c4, "a*", "b*", "c*"));
