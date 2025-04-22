	@Test
	public void testGetSet() throws Exception {
		URIish u = new URIish(str);
		u = u.setHost(u.getHost());
		u = u.setPass(u.getPass());
		u = u.setPort(u.getPort());
		assertEquals("ssh"
		assertTrue(u.isRemote());
		u = u.setRawPath(u.getRawPath());
		assertEquals("/some/p ath%20"
		u = u.setPath(u.getPath());
		assertEquals("/some/p ath "
		assertEquals("/some/p ath "
		assertEquals("example.com"
		assertEquals("DOMAIN\\user"
		assertEquals("pass"
		assertEquals(33
				u.toPrivateString());
				u.toPrivateASCIIString());
		assertEquals(u.setPass(null).toPrivateString()
		assertEquals(u.setPass(null).toPrivateASCIIString()
		assertEquals(u
	}

