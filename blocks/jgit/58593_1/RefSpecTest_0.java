	@Test
	public void testWildcardAfterText1() {
		assertTrue(a.isWildcard());
		assertTrue(a.matchDestination("refs/remotes/mine/x-blah"));
		assertTrue(a.matchDestination("refs/remotes/mine/foo-blah"));
		assertTrue(a.matchDestination("refs/remotes/mine/foo/x-blah"));
		assertFalse(a.matchDestination("refs/remotes/origin/foo/x-blah"));

		RefSpec b = a.expandFromSource("refs/heads/foo/for-linus");
		assertEquals("refs/remotes/mine/foo-blah"
		RefSpec c = a.expandFromDestination("refs/remotes/mine/foo-blah");
		assertEquals("refs/heads/foo/for-linus"
	}

	@Test
	public void testWildcardAfterText2() {
		assertTrue(a.isWildcard());
		assertTrue(a.matchSource("refs/headsx/for-linus"));
		assertTrue(a.matchSource("refs/headsfoo/for-linus"));
		assertTrue(a.matchSource("refs/headsx/foo/for-linus"));
		assertFalse(a.matchSource("refs/headx/for-linus"));

		RefSpec b = a.expandFromSource("refs/headsx/for-linus");
		assertEquals("refs/remotes/mine/x"
		RefSpec c = a.expandFromDestination("refs/remotes/mine/x");
		assertEquals("refs/headsx/for-linus"

		RefSpec d = a.expandFromSource("refs/headsx/foo/for-linus");
		assertEquals("refs/remotes/mine/x/foo"
		RefSpec e = a.expandFromDestination("refs/remotes/mine/x/foo");
		assertEquals("refs/headsx/foo/for-linus"
	}

