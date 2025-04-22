	@Test(expected = IllegalArgumentException.class)
	public void testIllegal() {
		decode("0011test00");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegal2() {
		decode("0123456789abcdefgh");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegal3() {
		decode("0123456789abcdef-_+*");
	}

	@Test
	public void testLegal() {
		decode("0123456789abcdef");
	}

	@Test
	public void testLegal2() {
		decode("deadbeef");
	}

