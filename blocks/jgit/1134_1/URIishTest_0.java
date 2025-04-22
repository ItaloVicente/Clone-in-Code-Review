	public void testGitWithUserHome() throws Exception {
		URIish u = new URIish(str);
		assertEquals("git"
		assertTrue(u.isRemote());
		assertEquals("~some/p ath"
		assertEquals("example.com"
		assertNull(u.getUser());
		assertNull(u.getPass());
		assertEquals(-1
		assertEquals(str
		assertEquals(u.setPass(null).toPrivateString()
		assertEquals(u
	}

	public void testFileWithUserHome() throws Exception {
		final String str = "~some/p ath";
		URIish u = new URIish(str);
		assertEquals("git"
		assertTrue(u.isRemote());
		assertEquals("~some/p ath"
		assertEquals("example.com"
		assertNull(u.getUser());
		assertNull(u.getPass());
		assertEquals(-1
		assertEquals(str
		assertEquals(u.setPass(null).toPrivateString()
		assertEquals(u
	}

	public void testFileWithNoneUserHomeWithTilde() throws Exception {
		final String str = "/~some/p ath";
		URIish u = new URIish(str);
		assertNull(u.getScheme());
		assertFalse(u.isRemote());
		assertEquals("/~some/p ath"
		assertNull(u.getHost());
		assertNull(u.getUser());
		assertNull(u.getPass());
		assertEquals(-1
		assertEquals(str
		assertEquals(u.setPass(null).toPrivateString()
		assertEquals(u
	}

