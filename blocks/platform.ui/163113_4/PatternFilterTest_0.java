	@Test
	public void testStringMatcher() {
		assertTrue(new StringMatcher("huhn h?hner", false, false).match("hahn henne hühner küken huhn"));
		assertFalse(new StringMatcher("fo*ar", false, false).match("foobar_123"));
		assertFalse(new StringMatcher("fo*ar", false, false).match("foobar_baz"));
	}

