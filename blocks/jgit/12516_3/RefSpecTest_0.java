
	@Test
	public void isWildcardShouldWorkForWildcardSuffixAndComponent() {
		assertFalse(RefSpec.isWildcard("refs/heads/a"));
	}

	@Test
	public void testWildcardInMiddleOfSource() {
		assertTrue(a.isWildcard());
		assertTrue(a.matchSource("refs/pull/a/head"));
		assertTrue(a.matchSource("refs/pull/foo/head"));
		assertTrue(a.matchSource("refs/pull/foo/bar/head"));
		assertFalse(a.matchSource("refs/pull/foo"));
		assertFalse(a.matchSource("refs/pull/head"));
		assertFalse(a.matchSource("refs/pull/foo/head/more"));
		assertFalse(a.matchSource("refs/pullx/head"));

		RefSpec b = a.expandFromSource("refs/pull/foo/head");
		assertEquals("refs/remotes/origin/pr/foo"
		RefSpec c = a.expandFromDestination("refs/remotes/origin/pr/foo");
		assertEquals("refs/pull/foo/head"
	}

	@Test
	public void testWildcardInMiddleOfDestionation() {
		assertTrue(a.isWildcard());
		assertTrue(a.matchDestination("refs/remotes/origin/a/head"));
		assertTrue(a.matchDestination("refs/remotes/origin/foo/head"));
		assertTrue(a.matchDestination("refs/remotes/origin/foo/bar/head"));
		assertFalse(a.matchDestination("refs/remotes/origin/foo"));
		assertFalse(a.matchDestination("refs/remotes/origin/head"));
		assertFalse(a.matchDestination("refs/remotes/origin/foo/head/more"));
		assertFalse(a.matchDestination("refs/remotes/originx/head"));

		RefSpec b = a.expandFromSource("refs/heads/foo");
		assertEquals("refs/remotes/origin/foo/head"
		RefSpec c = a.expandFromDestination("refs/remotes/origin/foo/head");
		assertEquals("refs/heads/foo"
	}

	@Test
	public void testWildcardMirror() {
		RefSpec a = new RefSpec("*:*");
		assertTrue(a.isWildcard());
		assertTrue(a.matchSource("a"));
		assertTrue(a.matchSource("foo"));
		assertTrue(a.matchSource("foo/bar"));
		assertTrue(a.matchDestination("a"));
		assertTrue(a.matchDestination("foo"));
		assertTrue(a.matchDestination("foo/bar"));

		RefSpec b = a.expandFromSource("refs/heads/foo");
		assertEquals("refs/heads/foo"
		RefSpec c = a.expandFromDestination("refs/heads/foo");
		assertEquals("refs/heads/foo"
	}

	@Test
	public void testWildcardAtStart() {
		assertTrue(a.isWildcard());
		assertTrue(a.matchSource("a/head"));
		assertTrue(a.matchSource("foo/head"));
		assertTrue(a.matchSource("foo/bar/head"));
		assertFalse(a.matchSource("/head"));
		assertFalse(a.matchSource("a/head/extra"));

		RefSpec b = a.expandFromSource("foo/head");
		assertEquals("refs/heads/foo"
		RefSpec c = a.expandFromDestination("refs/heads/foo");
		assertEquals("foo/head"
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
	public void invalidWhenWildcardBeforeText() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidWhenWildcardBeforeTextAtEnd() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidSourceDoubleSlashes() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidSlashAtStart() {
		assertNotNull(new RefSpec("/foo:/foo"));
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
