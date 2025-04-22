		assertNotNull(
				"The part should have been created so it should have a context",
				partA2.getContext());
		assertEquals(
				"This part has not been instantiated yet, it should have no context",
				null, partB2.getContext());
