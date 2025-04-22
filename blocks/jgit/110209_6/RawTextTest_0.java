	@Test
	public void testBinary() {
		String input = "foo-a\nf\0o-b\n";
		byte[] data = Constants.encodeASCII(input);
		final RawText a = new RawText(data);
		assertEquals(a.content
		assertEquals(a.size()
		assertEquals(a.getString(0
	}

