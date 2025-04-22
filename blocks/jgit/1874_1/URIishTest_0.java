	public void testScpStyleWithoutUserAbsolutePath() throws Exception {
		final String str = "example.com:/some/p ath";
		URIish u = new URIish(str);
		assertNull(u.getScheme());
		assertTrue(u.isRemote());
		assertEquals("/some/p ath"
		assertEquals("example.com"
		assertEquals(-1
		assertEquals(str
		assertEquals(u
	}

