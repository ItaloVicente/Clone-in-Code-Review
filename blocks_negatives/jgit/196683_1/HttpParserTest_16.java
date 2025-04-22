		assertEquals("Unexpected number of challenges", 2, challenges.size());
		assertNull("No token expected", challenges.get(0).getToken());
		assertNull("No token expected", challenges.get(1).getToken());
		assertEquals("Unexpected mechanism", "Newauth",
				challenges.get(0).getMechanism());
		assertEquals("Unexpected mechanism", "Basic",
				challenges.get(1).getMechanism());
