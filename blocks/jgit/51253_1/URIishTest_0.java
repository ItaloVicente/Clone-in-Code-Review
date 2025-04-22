	@Test
	public void testFileProtoWithHost() throws Exception {
		URIish u = new URIish(str);
		assertEquals("file"
		assertTrue(u.isRemote());
		assertEquals("192.168.1.1"
		assertEquals("/home/m y"
		assertEquals("/home/m y"
		assertEquals(u
	}

