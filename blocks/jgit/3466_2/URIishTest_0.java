	@Test
	public void shouldRaiseErrorOnEmptyURI() throws Exception {
		try {
			new URIish("");
			fail("expecting an exception");
		} catch (URISyntaxException e) {
		}
	}

	@Test
	public void shouldRaiseErrorOnNullURI() throws Exception {
		try {
			new URIish((String) null);
			fail("expecting an exception");
		} catch (URISyntaxException e) {
		}
	}

