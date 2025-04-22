
	@Test
	public void testNoQuote() {
		assertSame("\u00c5ngstr\u00f6m"
				GIT_PATH_MINIMAL.quote("\u00c5ngstr\u00f6m"));
	}

	@Test
	public void testQuoteMinimal() {
		assertEquals("\"\u00c5n\\\\str\u00f6m\""
				GIT_PATH_MINIMAL.quote("\u00c5n\\str\u00f6m"));
	}

	@Test
	public void testDequoteMinimal() {
		assertEquals("\u00c5n\\str\u00f6m"
				.dequote(GIT_PATH_MINIMAL.quote("\u00c5n\\str\u00f6m")));

	}

	@Test
	public void testRoundtripMinimal() {
		assertEquals("\u00c5ngstr\u00f6m"
				.dequote(GIT_PATH_MINIMAL.quote("\u00c5ngstr\u00f6m")));

	}

	@Test
	public void testQuoteMinimalDequoteNormal() {
		assertEquals("\u00c5n\\str\u00f6m"
				.dequote(GIT_PATH_MINIMAL.quote("\u00c5n\\str\u00f6m")));

	}

	@Test
	public void testQuoteNormalDequoteMinimal() {
		assertEquals("\u00c5n\\str\u00f6m"
				.dequote(GIT_PATH.quote("\u00c5n\\str\u00f6m")));

	}

	@Test
	public void testRoundtripMinimalDequoteNormal() {
		assertEquals("\u00c5ngstr\u00f6m"
				GIT_PATH.dequote(GIT_PATH_MINIMAL.quote("\u00c5ngstr\u00f6m")));

	}

	@Test
	public void testRoundtripNormalDequoteMinimal() {
		assertEquals("\u00c5ngstr\u00f6m"
				GIT_PATH_MINIMAL.dequote(GIT_PATH.quote("\u00c5ngstr\u00f6m")));

	}

	@Test
	public void testDequote_UTF8_Minimal() {
		assertDequoteMinimal("\u00c5ngstr\u00f6m"
				"\\303\\205ngstr\\303\\266m");
	}

	@Test
	public void testDequote_RawUTF8_Minimal() {
		assertDequoteMinimal("\u00c5ngstr\u00f6m"
	}

	@Test
	public void testDequote_RawLatin1_Minimal() {
		assertDequoteMinimal("\u00c5ngstr\u00f6m"
	}

