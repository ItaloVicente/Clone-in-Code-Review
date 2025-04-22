	@Test
	public void testFileProtoWindowsWithHost() throws Exception {
		URIish u = new URIish(str);
		assertEquals("file"
		assertTrue(u.isRemote());
		assertEquals("localhost"
		assertEquals(-1
		assertEquals(null
		assertEquals(null
		assertEquals("D:/m y"
		assertEquals("D:/m y"
		assertEquals(u
	}

	@Test
	public void testFileProtoWindowsWithHostAndPort() throws Exception {
		URIish u = new URIish(str);
		assertEquals("file"
		assertTrue(u.isRemote());
		assertEquals("localhost"
		assertEquals(80
		assertEquals(null
		assertEquals(null
		assertEquals("D:/m y"
		assertEquals("D:/m y"
		assertEquals(u
	}

	@Test
	public void testFileProtoWindowsWithHostAndEmptyPortIsAmbiguous()
			throws Exception {
		URIish u = new URIish(str);
		assertEquals("file"
		assertFalse(u.isRemote());
		assertEquals(null
		assertEquals(-1
		assertEquals(null
		assertEquals(null
		assertEquals("localhost:/D:/m y"
		assertEquals("localhost:/D:/m y"
		assertEquals(u
	}

	@Test
	public void testFileProtoWindowsMissingHostSlash() throws Exception {
		URIish u = new URIish(str);
		assertEquals("file"
		assertFalse(u.isRemote());
		assertEquals("D:/m y"
		assertEquals("D:/m y"
		assertEquals(u
	}

	@Test
	public void testFileProtoWindowsMissingHostSlash2() throws Exception {
		URIish u = new URIish(str);
		assertEquals("file"
		assertFalse(u.isRemote());
		assertEquals("D: /m y"
		assertEquals("D: /m y"
		assertEquals(u
	}

