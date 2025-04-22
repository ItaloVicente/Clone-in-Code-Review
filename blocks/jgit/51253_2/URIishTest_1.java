	@Test
	public void testGetEmptyHumanishNameWithAuthorityOnly() throws IllegalArgumentException
			URISyntaxException {
		String humanishName = new URIish(GIT_SCHEME + "abc").getHumanishName();
		assertEquals("abc"
	}

