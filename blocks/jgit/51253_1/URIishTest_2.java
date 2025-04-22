	@Test
	public void testGetEmptyHumanishNameWithAuthorityOnly() throws IllegalArgumentException
			URISyntaxException {
		try {
			new URIish(GIT_SCHEME + "abc").getHumanishName();
			fail("empty path is useless");
		} catch (IllegalArgumentException e) {
		}
	}

