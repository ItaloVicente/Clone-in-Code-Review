	@Test
	public void testSshProtoHostWithEmptyPortAndPath() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh"
		assertTrue(u.isRemote());
		assertEquals("/path"
		assertEquals("/path"
		assertEquals("example.com"
		assertEquals(-1
		assertEquals(u
		assertEquals(u
	}

