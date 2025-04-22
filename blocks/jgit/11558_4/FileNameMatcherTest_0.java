	@Test
	public void testEscapedBracket1() throws Exception {
		assertMatch("\\["
	}

	@Test
	public void testEscapedBracket2() throws Exception {
		assertMatch("\\[[a]"
	}

	@Test
	public void testEscapedBracket3() throws Exception {
		assertMatch("\\[[a]"
	}

	@Test
	public void testEscapedBracket4() throws Exception {
		assertMatch("\\[[a]"
	}

	@Test
	public void testEscapedBracket5() throws Exception {
		assertMatch("[a\\]]"
	}

	@Test
	public void testEscapedBracket6() throws Exception {
		assertMatch("[a\\]]"
	}

	@Test
	public void testEscapedBackslash() throws Exception {
		assertMatch("a\\\\b"
	}

	@Test
	public void testMultipleEscapedCharacters1() throws Exception {
		assertMatch("\\]a?c\\*\\[d\\?\\]"
	}

