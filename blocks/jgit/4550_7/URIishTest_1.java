		assertEquals(u
	}

	@Test
	public void testScpStyleNoURIDecoding() throws Exception {
		final String str = "example.com:some/p%20ath";
		URIish u = new URIish(str);
		assertNull(u.getScheme());
		assertTrue(u.isRemote());
		assertEquals("some/p%20ath"
		assertEquals("some/p%20ath"
		assertEquals("example.com"
		assertEquals(-1
