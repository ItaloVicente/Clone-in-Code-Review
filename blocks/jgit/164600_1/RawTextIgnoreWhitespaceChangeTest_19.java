
	@Test
	public void testEqualsWithTabs() {
		RawText a = new RawText(
				Constants.encodeASCII("a\tb\t \na\tb\t c \n  foo\na b\na  b"));
		RawText b = new RawText(
				Constants.encodeASCII("a b \na b c\n\tfoo\nab\na \tb"));

		assertTrue(cmp.equals(a
		assertTrue(cmp.equals(b

		assertTrue(cmp.equals(a
		assertTrue(cmp.equals(b

		assertTrue(cmp.equals(a
		assertTrue(cmp.equals(b

		assertFalse(cmp.equals(a
		assertFalse(cmp.equals(b

		assertTrue(cmp.equals(a
		assertTrue(cmp.equals(b
	}

	@Test
	public void testHashCode() {
		RawText a = new RawText(Constants
				.encodeASCII("a b  c\n\nab  c d  \n\ta bc d\nxyz\na  b  c"));
		RawText b = new RawText(Constants.encodeASCII(
				"a b  c\na   b c\nab  c d\na bc d\n  \t a bc d\na b c\n"));

		assertEquals(cmp.hash(a

		assertEquals(cmp.hash(a

		assertEquals(cmp.hash(a

		assertEquals(cmp.hash(new RawText(Constants.encodeASCII(" "))
				cmp.hash(new RawText(Constants.encodeASCII("\t"))

		assertEquals(cmp.hash(a

		assertEquals(cmp.hash(a

		assertNotEquals(cmp.hash(a

		assertEquals(cmp.hash(a

		assertNotEquals(cmp.hash(a

		assertEquals(cmp.hash(a

	}
