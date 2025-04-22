	@Test
	public void testDestinationContains() {
		assertTrue(a.destinationContains("refs/remotes/origin/master"));
		assertTrue(a.destinationContains("refs/remotes/origin/foo/bar/baz"));

		assertFalse(a.destinationContains("refs/heads/master"));


		assertFalse(b.destinationContains("refs/remotes/origin"));

	}

