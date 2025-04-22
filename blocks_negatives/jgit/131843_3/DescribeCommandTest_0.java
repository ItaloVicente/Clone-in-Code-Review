		assertEquals("v1.0.0", describe(c1));
		assertEquals("v1.0.0-1-g3747db3", describe(c2));

		assertEquals("v1.0.0", describe(c1, "v1.0*"));
		assertEquals("v1.1.1", describe(c1, "v1.1*"));
		assertEquals("v1.0.0-1-g3747db3", describe(c2, "v1.0*"));
		assertEquals("v1.1.1-1-g3747db3", describe(c2, "v1.1*"));

		assertEquals("v1.0.0", describe(c1, "v1.0*", "v1.1*"));
		assertEquals("v1.1.1", describe(c1, "v1.1*", "v1.0*"));
		assertEquals("v1.0.0-1-g3747db3", describe(c2, "v1.0*", "v1.1*"));
		assertEquals("v1.1.1-1-g3747db3", describe(c2, "v1.1*", "v1.0*"));
