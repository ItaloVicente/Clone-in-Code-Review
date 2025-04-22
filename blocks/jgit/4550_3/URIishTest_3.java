	@Test
	public void testPathSeparator() throws URISyntaxException {
		URIish u = new URIish(str);
		assertEquals("http"
		assertTrue(u.isRemote());
		assertEquals("/some%2Fpath"
		assertEquals("/some/path"
		assertEquals("host.xy"
		assertEquals(80
		assertEquals("user"
		assertEquals("secret"
		assertEquals(u
	}

