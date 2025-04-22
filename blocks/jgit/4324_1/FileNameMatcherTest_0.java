	@Test
	public void testUselessEscaping0() throws Exception {
		assertFileNameMatch("a\\a"
	}

	@Test
	public void testUselessEscaping1() throws Exception {
		assertFileNameMatch("a\\a"
	}

	@Test
	public void testEscaping0() throws Exception {
		assertFileNameMatch("\\\\"
	}

	@Test
	public void testEscaping1() throws Exception {
		assertFileNameMatch("\\["
	}

	@Test
	public void testEscaping2() throws Exception {
		assertFileNameMatch("\\["
	}

	@Test
	public void testEscaping3() throws Exception {
		assertFileNameMatch("\\]"
	}

	@Test
	public void testEscaping4() throws Exception {
		assertFileNameMatch("\\]"
	}

	@Test
	public void testEscaping5() throws Exception {
		assertFileNameMatch("\\*"
	}

	@Test
	public void testEscaping6() throws Exception {
		assertFileNameMatch("\\*"
	}

	@Test
	public void testEscaping7() throws Exception {
		assertFileNameMatch("\\?"
	}

	@Test
	public void testEscaping8() throws Exception {
		assertFileNameMatch("\\?"
	}

	@Test
	public void testEscaping9() throws Exception {
		assertFileNameMatch("\\!"
	}

	@Test
	public void testEscaping10() throws Exception {
		assertFileNameMatch("\\!"
	}

	@Test
	public void testEscaping11() throws Exception {
		assertFileNameMatch("\\[ab\\]"
	}

	@Test
	public void testEscaping12() throws Exception {
		assertFileNameMatch("\\[ab\\]"
	}

	@Test
	public void testEscaping13() throws Exception {
		assertFileNameMatch("\\[ab\\]"
	}

	@Test
	public void testBracketsClosingAndEscaping0() throws Exception {
		assertFileNameMatch("[\\!\\]]"
	}

	@Test
	public void testBracketsClosingAndEscaping1() throws Exception {
		assertFileNameMatch("[\\!\\]]"
	}

	@Test
	public void testBracketsClosingAndEscaping2() throws Exception {
		assertFileNameMatch("[\\!\\]]"
	}

	@Test
	public void testBracketsClosingAndEscaping3() throws Exception {
		assertFileNameMatch("[!\\!\\]]"
	}

	@Test
	public void testBracketsClosingAndEscaping4() throws Exception {
		assertFileNameMatch("[!\\!\\]]"
	}

	@Test
	public void testBracketsClosingAndEscaping5() throws Exception {
		assertFileNameMatch("[!\\!\\]]"
	}

	@Test
	public void testBackSlashIsSeparatorAndEscaping0() throws Exception {
		assertFileNameMatch("a\\b"
	}

	@Test
	public void testBackSlashIsSeparatorAndEscaping1() throws Exception {
		assertFileNameMatch("a\\b"
	}

	@Test
	public void testPatternEndsWithBackslash0() throws Exception {
		try {
			assertMatch("\\"
			fail("InvalidPatternException expected");
		} catch (InvalidPatternException e) {
		}
	}

	@Test
	public void testPatternEndsWithBackslash1() throws Exception {
		try {
			assertMatch("\\\\\\"
			fail("InvalidPatternException expected");
		} catch (InvalidPatternException e) {
		}
	}

