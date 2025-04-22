	@Test
	public void testNoFinalNewline() throws ConfigInvalidException {
		Config c = parse("[a]\n"
				+ "x = 0\n"
				+ "y = 1");
		assertEquals("0"
		assertEquals("1"
	}

