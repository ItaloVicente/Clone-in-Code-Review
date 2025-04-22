		assertNameStartsWith(c4, "3e563c5");
		assertEquals("bob-t2-1-g3e563c5", describe(c4));
		assertEquals("bob-t2-1-g3e563c5", describe(c4, true));
		assertEquals("alice-t1-2-g3e563c5", describe(c4, "alice*"));
		assertEquals("bob-t2-1-g3e563c5", describe(c4, "bob*"));
		assertEquals("bob-t2-1-g3e563c5", describe(c4, "a*", "b*", "c*"));
