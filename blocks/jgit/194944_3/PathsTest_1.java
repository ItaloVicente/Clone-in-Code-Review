	@Test
	public void testPrefix() {
		assertTrue(Paths.isEqualOrPrefix("a"
		assertTrue(Paths.isEqualOrPrefix("a"
		assertTrue(Paths.isEqualOrPrefix("a"
		assertFalse(Paths.isEqualOrPrefix("a"
		assertFalse(Paths.isEqualOrPrefix("a"
		assertFalse(Paths.isEqualOrPrefix("a"
		assertFalse(Paths.isEqualOrPrefix("a"
		assertFalse(Paths.isEqualOrPrefix("a"
		assertFalse(Paths.isEqualOrPrefix(""
		assertTrue(Paths.isEqualOrPrefix(""
		assertTrue(Paths.isEqualOrPrefix("a/b"
		assertTrue(Paths.isEqualOrPrefix("a/b"
		assertFalse(Paths.isEqualOrPrefix("a/b"
	}

