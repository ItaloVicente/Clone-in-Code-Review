	@Test
	public void testSshProtoWithEmailUserAndPort() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh"
		assertTrue(u.isRemote());
		assertEquals("/some/p ath"
		assertEquals("/some/p ath"
		assertEquals("example.com"
		assertEquals("user.name@email.com"
		assertNull(u.getPass());
		assertEquals(33
				u.toPrivateString());
				u.toPrivateASCIIString());
		assertEquals(u.setPass(null).toPrivateString()
		assertEquals(u.setPass(null).toPrivateASCIIString()
		assertEquals(u
	}

	@Test
	public void testSshProtoWithEmailUserPassAndPort() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh"
		assertTrue(u.isRemote());
		assertEquals("/some/p ath"
		assertEquals("/some/p ath"
		assertEquals("example.com"
		assertEquals("user.name@email.com"
		assertEquals("pass@wor:d"
		assertEquals(33
				u.toPrivateString());
				u.toPrivateASCIIString());
		assertEquals(u.setPass(null).toPrivateString()
		assertEquals(u.setPass(null).toPrivateASCIIString()
		assertEquals(u
	}

