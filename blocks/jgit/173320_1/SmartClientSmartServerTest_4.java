		List<AccessEvent> requests = getRequests();
		assertEquals(3

		AccessEvent redirect = requests.get(0);
		assertEquals("GET"
		assertEquals(join(cloneFrom
		assertEquals(301

		assertFetchRequests(requests
