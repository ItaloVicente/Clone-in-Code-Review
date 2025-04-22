	@Test
	public void testDestinationContains() {
		assertTrue(a.destinationContains("refs/remotes/origin/master"));

		assertFalse(a.destinationContains("refs/heads/master"));
		assertFalse(a.destinationContains("refs/remotes/origin"));
	}

