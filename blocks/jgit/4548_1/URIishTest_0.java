	@Test
	public void testSshProtoWithADUserPassAndPort() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh"
		assertTrue(u.isRemote());
		assertEquals("/some/p ath"
		assertEquals("example.com"
		assertEquals("DOMAIN\\user"
		assertEquals("pass"
		assertEquals(33
		assertEquals(str
		assertEquals(u.setPass(null).toPrivateString()
		assertEquals(u
	}

