	@Test
	public void testEmptyValueAtEof() throws Exception {
		String text = "[a]\nx =";
		Config c = parse(text);
		assertEquals(""
		c = parse(text + "\n");
		assertEquals(""
	}

