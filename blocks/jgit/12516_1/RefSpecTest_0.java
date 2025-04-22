
	@Test
	public void isWildcardShouldWorkForWildcardSuffixAndComponent() {
		assertFalse(RefSpec.isWildcard("refs/heads/a"));
	}

	@Test
	public void testWildcardInMiddleOfSource() {
		assertTrue(a.isWildcard());
		assertTrue(a.matchSource("refs/pull/foo/head"));
		assertFalse(a.matchSource("refs/pull/foo"));
		assertFalse(a.matchSource("refs/pull/head"));
		assertFalse(a.matchSource("refs/pull/foo/head/more"));

		RefSpec b = a.expandFromSource("refs/pull/foo/head");
		assertEquals("refs/remotes/origin/pr/foo"
		RefSpec c = a.expandFromDestination("refs/remotes/origin/pr/foo");
		assertEquals("refs/pull/foo/head"
	}

	@Test
	public void testWildcardInMiddleOfDestionation() {
		assertTrue(a.isWildcard());
		assertTrue(a.matchDestination("refs/remotes/origin/foo/head"));
		assertFalse(a.matchDestination("refs/remotes/origin/foo"));
		assertFalse(a.matchDestination("refs/remotes/origin/head"));
		assertFalse(a.matchDestination("refs/remotes/origin/foo/head/more"));

		RefSpec b = a.expandFromSource("refs/heads/foo");
		assertEquals("refs/remotes/origin/foo/head"
		RefSpec c = a.expandFromDestination("refs/remotes/origin/foo/head");
		assertEquals("refs/heads/foo"
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidWhenSourceOnlyAndWildcard() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidWhenDestinationOnlyAndWildcard() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidWhenOnlySourceWildcard() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidWhenOnlyDestinationWildcard() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidWhenMoreThanOneWildcardInSource() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidWhenMoreThanOneWildcardInDestination() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidWhenWildcardAfterText() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidSourceDoubleSlashes() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidDestinationDoubleSlashes() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidSetSource() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidSetDestination() {
	}
