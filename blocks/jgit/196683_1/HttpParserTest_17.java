		assertEquals(6
		assertEquals("Other"
				"Mismatched challenge");
		assertEquals("0123456789==="
				"Token expected");
		assertEquals("YetAnother"
				"Mismatched challenge");
		assertNull(challenges.get(3).getToken()
		assertTrue(challenges.get(3).getArguments().isEmpty()
				"No arguments expected");
		assertEquals("Negotiate"
				"Mismatched challenge");
		assertNull(challenges.get(4).getToken()
		assertEquals("Negotiate"
				"Mismatched challenge");
		assertEquals("a87421000492aa874209af8bc028"
				challenges.get(5).getToken()
