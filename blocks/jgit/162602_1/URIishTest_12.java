
		u = new URIish("file:/a/test.bundle");
		assertEquals("file"
		assertFalse(u.isRemote());
		assertNull(u.getHost());
		assertNull(u.getPass());
		assertEquals("/a/test.bundle"
		assertEquals("/a/test.bundle"
		assertEquals(-1
		assertNull(u.getUser());
		assertEquals("test"
