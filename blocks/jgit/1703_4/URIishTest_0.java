	public void testFileProtocol() throws IllegalArgumentException
			URISyntaxException
		assertEquals("file"
		assertFalse(u.isRemote());
		assertNull(u.getHost());
		assertNull(u.getPass());
		assertEquals("/a/b.txt"
		assertEquals(-1
		assertNull(u.getUser());
		assertEquals("b.txt"

		File tmp = File.createTempFile("jgitUnitTest"
		u = new URIish(tmp.toURI().toString());
		assertEquals("file"
		assertFalse(u.isRemote());
		assertNull(u.getHost());
		assertNull(u.getPass());
		assertTrue(u.getPath().contains("jgitUnitTest"));
		assertEquals(-1
		assertNull(u.getUser());
		assertTrue(u.getHumanishName().startsWith("jgitUnitTest"));

		u = new URIish("file:/a/b.txt");
		assertEquals("file"
		assertFalse(u.isRemote());
		assertNull(u.getHost());
		assertNull(u.getPass());
		assertEquals("/a/b.txt"
		assertEquals(-1
		assertNull(u.getUser());
		assertEquals("b.txt"
	}

