		assertEquals(u
	}

	@Test
	public void testURIEncode_00() throws Exception {
		URIish u = new URIish(str);
		assertEquals("file"
		assertFalse(u.isRemote());
		assertEquals("/home/m\u0000y"
		assertEquals(u
	}

	@Test
	public void testURIEncode_0a() throws Exception {
		URIish u = new URIish(str);
		assertEquals("file"
		assertFalse(u.isRemote());
		assertEquals("/home/m\ny"
		assertEquals(u
	}

	@Test
	public void testURIEncode_unicode() throws Exception {
		URIish u = new URIish(str);
		assertEquals("file"
		assertFalse(u.isRemote());
		assertEquals("/home/m\u00e5y"
