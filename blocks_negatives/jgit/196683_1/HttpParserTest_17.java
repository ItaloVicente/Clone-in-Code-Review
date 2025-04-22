		assertEquals("Unexpected number of challenges", 6, challenges.size());
		assertEquals("Mismatched challenge", "Other",
				challenges.get(2).getMechanism());
		assertEquals("Token expected", "0123456789===",
				challenges.get(2).getToken());
		assertEquals("Mismatched challenge", "YetAnother",
				challenges.get(3).getMechanism());
		assertNull("No token expected", challenges.get(3).getToken());
		assertTrue("No arguments expected",
				challenges.get(3).getArguments().isEmpty());
		assertEquals("Mismatched challenge", "Negotiate",
				challenges.get(4).getMechanism());
		assertNull("No token expected", challenges.get(4).getToken());
		assertEquals("Mismatched challenge", "Negotiate",
				challenges.get(5).getMechanism());
		assertEquals("Token expected", "a87421000492aa874209af8bc028",
				challenges.get(5).getToken());
