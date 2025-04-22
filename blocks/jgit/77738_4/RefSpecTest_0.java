
	@Test
	public void sourceOnlywithWildcard() {
		assertTrue(a.matchSource("refs/heads/master"));
		assertTrue(a.matchDestination("refs/heads/master"));
	}

	@Test
	public void destinationWithWildcard() {
		assertTrue(a.matchSource("refs/heads/master"));
		assertTrue(a.matchDestination("refs/heads/master"));
		assertTrue(a.matchDestination("refs/heads/foo"));
	}

	@Test
	public void onlyWildCard() {
		RefSpec a = new RefSpec("*"
		assertTrue(a.matchSource("refs/heads/master"));
		assertTrue(a.matchDestination("refs/heads/master"));
	}
