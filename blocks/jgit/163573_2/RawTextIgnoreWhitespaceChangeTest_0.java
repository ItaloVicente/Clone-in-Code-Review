
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
