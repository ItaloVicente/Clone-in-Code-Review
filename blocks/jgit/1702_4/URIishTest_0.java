	public void testUserPasswordAndPort() throws URISyntaxException {
		URIish u = new URIish(str);
		assertEquals("http"
		assertTrue(u.isRemote());
		assertEquals("/some/path"
		assertEquals("host.xy"
		assertEquals(80
		assertEquals("user"
		assertEquals("secret"
		assertEquals(u

		u = new URIish(str);
		assertEquals("http"
		assertTrue(u.isRemote());
		assertEquals("/some/path"
		assertEquals("host.xy"
		assertEquals(80
		assertEquals("user"
		assertEquals("secret@pass"
		assertEquals(u
	}
