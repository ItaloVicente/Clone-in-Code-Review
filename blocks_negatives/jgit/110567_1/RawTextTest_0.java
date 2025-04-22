	@Test
	public void testBinary() {
		String input = "foo-a\nf\0o-b\n";
		byte[] data = Constants.encodeASCII(input);
		final RawText a = new RawText(data);
		assertEquals(a.content, data);
		assertEquals(a.size(), 1);
		assertEquals(a.getString(0, 1, false), input);
	}

