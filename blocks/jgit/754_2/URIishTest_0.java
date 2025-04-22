	public void testStringConstructor() throws Exception {
		assertEquals("http"
		assertEquals("some.hostname.com"
		assertEquals("/foo/bar"

		assertEquals("http"
		assertEquals("some.hostname.com"
		assertEquals(8080
		assertEquals("/foo/bar"

		uri = new URIish(
		assertEquals("http"
		assertEquals("anonymous"
		assertEquals("secret"
		assertEquals("some.hostname.com"
		assertEquals(8080
		assertEquals("/foo/bar"

		uri = new URIish("some.hostname.com:/foo/bar");
		assertEquals("some.hostname.com"
		assertEquals("/foo/bar"

		assertEquals("http"
		assertEquals("some.hostname.com"
		assertEquals("/foo/bar"
	}

	public void testStringConstructorErrorCases() throws Exception {
		try {
			URIish uri = new URIish("some.hostname.com/foo/bar");
			fail("Should have thrown a URISyntaxException for " + uri);
		} catch (URISyntaxException e) {
		}
	}
