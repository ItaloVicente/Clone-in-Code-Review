	@Test
	public void testTrimWhitespace() {
		assertTrue(Strings.isDirectoryPattern("/"));
		assertTrue(Strings.isDirectoryPattern("/ "));
		assertTrue(Strings.isDirectoryPattern("/     "));
		assertFalse(Strings.isDirectoryPattern("     "));
		assertFalse(Strings.isDirectoryPattern(""));
	}

	@Test
	public void testStripTrailingChar() {
		assertEquals(""
		assertEquals(""
		assertEquals("a"
		assertEquals("a"
		assertEquals("a/ "
	}

	@Test
	public void testStripTrailingWhitespace() {
		assertEquals(""
		assertEquals(""
		assertEquals("a"
		assertEquals("a"
		assertEquals("a"
		assertEquals("a"
	}
