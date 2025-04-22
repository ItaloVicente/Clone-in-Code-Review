	@Test
	public void testSshProtoHostOnly() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh"
		assertTrue(u.isRemote());
		assertEquals("/"
		assertEquals("/"
		assertEquals("example.com"
		assertEquals(-1
		assertEquals("example.com"
		assertEquals(u
	}

	@Test
	public void testSshProtoHostWithAuthentication() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh"
		assertTrue(u.isRemote());
		assertEquals("/"
		assertEquals("/"
		assertEquals("example.com"
		assertEquals(-1
		assertEquals("example.com"
		assertEquals("user"
		assertEquals("secret@pass"
		assertEquals(u
	}

	@Test
	public void testSshProtoHostWithPort() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh"
		assertTrue(u.isRemote());
		assertEquals("/"
		assertEquals("/"
		assertEquals("example.com"
		assertEquals(2222
		assertEquals("example.com"
		assertEquals(u
	}

