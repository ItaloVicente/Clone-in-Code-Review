
	@Test
	public void sourceOnlywithWildcard() {
				WildcardMode.ALLOW_MISMATCH);
		assertTrue(a.matchSource("refs/heads/master"));
		assertNull(a.getDestination());
	}

	@Test
	public void destinationWithWildcard() {
				WildcardMode.ALLOW_MISMATCH);
		assertTrue(a.matchSource("refs/heads/master"));
		assertTrue(a.matchDestination("refs/heads/master"));
		assertTrue(a.matchDestination("refs/heads/foo"));
	}

	@Test
	public void onlyWildCard() {
		RefSpec a = new RefSpec("*"
		assertTrue(a.matchSource("refs/heads/master"));
		assertNull(a.getDestination());
	}
