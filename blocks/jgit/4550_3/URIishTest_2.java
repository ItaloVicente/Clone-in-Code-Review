				u.toPrivateString());
		assertEquals(u.setPass(null).toPrivateString()
		assertEquals(u
	}

	@Test
	public void testSshProtoWithEscapedADUserPassAndPort() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh"
		assertTrue(u.isRemote());
		assertEquals("/some/p ath"
		assertEquals("/some/p ath"
		assertEquals("example.com"
		assertEquals("DOMAIN\\user"
		assertEquals("pass"
		assertEquals(33
				u.toPrivateString());
		assertEquals(u.setPass(null).toPrivateString()
		assertEquals(u
	}

	@Test
	public void testURIEncodeDecode() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh"
		assertTrue(u.isRemote());
		assertEquals("/some%c3%a5/p%20a th"
		assertEquals("/some\u00e5/p a th"
		assertEquals("example.com"
		assertEquals(":x%"
		assertEquals("@Ax"
		assertEquals(33
		assertEquals(
				u.toPrivateString());
