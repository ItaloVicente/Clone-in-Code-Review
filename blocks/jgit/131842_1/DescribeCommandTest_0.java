		if (useAnnotatedTags) {
			assertEquals("v1.1.1"
			assertEquals("v1.1.1-1-gb89dead"

			assertEquals("v1.0.1"
			assertEquals("v1.1.1"
			assertEquals("v1.0.1-1-gb89dead"
			assertEquals("v1.1.1-1-gb89dead"

			assertEquals("v1.0.1"
			assertEquals("v1.1.1"
			assertEquals("v1.0.1-1-gb89dead"
			assertEquals("v1.1.1-1-gb89dead"
		} else {
			assertNotNull(describe(c1));
			assertNotNull(describe(c2));

			assertNotNull(describe(c1
			assertNotNull(describe(c1
			assertNotNull(describe(c2
			assertNotNull(describe(c2

			assertNotNull(describe(c1
			assertNotNull(describe(c1
			assertNotNull(describe(c2
			assertNotNull(describe(c2

		}

