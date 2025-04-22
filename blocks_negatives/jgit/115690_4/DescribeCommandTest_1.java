		assertEquals("alice-t1", describe(c2, "a*", "b*", "c*"));

		assertEquals("bob-t2", describe(c3));
		assertEquals("bob-t2-0-g44579eb", describe(c3, true));
		assertEquals("alice-t1-1-g44579eb", describe(c3, "alice*"));
		assertEquals("alice-t1-1-g44579eb", describe(c3, "a??c?-t*"));
		assertEquals("bob-t2", describe(c3, "bob*"));
		assertEquals("bob-t2", describe(c3, "?ob*"));
		assertEquals("bob-t2", describe(c3, "a*", "b*", "c*"));
