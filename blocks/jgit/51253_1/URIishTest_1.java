	@Test
	public void testSshProtoHostOnly() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh"
		assertTrue(u.isRemote());
		assertEquals(null
		assertEquals(null
		assertEquals("example.com"
		assertEquals(-1
		assertEquals(u
	}

