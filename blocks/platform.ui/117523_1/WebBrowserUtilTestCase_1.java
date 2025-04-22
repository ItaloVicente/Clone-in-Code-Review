	private static final String URL = "http://127.0.0.1:3873/help/index.jsp";

	@SuppressWarnings("deprecation")
	@Test
	public void testNullParameters() {
		assertEquals(URL, WebBrowserUtil.createParameterString(null, URL));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testEmptyParameters() {
		assertEquals(URL, WebBrowserUtil.createParameterString("", URL));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testNullURL() {
		assertEquals("", WebBrowserUtil.createParameterString("", null));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testNoSubstitution() {
		assertEquals("-console " + URL, WebBrowserUtil.createParameterString("-console", URL));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testSubstitution() {
		assertEquals("-url " + URL + " -console", WebBrowserUtil.createParameterString("-url %URL% -console", URL));
	}

	@Test
	public void testArrayNullParameters() {
		assertArrayEquals(new String[] { URL }, WebBrowserUtil.createParameterArray(null, URL));
	}

	@Test
	public void testArrayEmptyParameters() {
		assertArrayEquals(new String[] { URL }, WebBrowserUtil.createParameterArray("", URL));
	}

	@Test
	public void testArrayNullURL() {
		assertArrayEquals(new String[0], WebBrowserUtil.createParameterArray("", null));
	}

	@Test
	public void testArrayNoSubstitution() {
		assertArrayEquals(new String[] { "-console", URL }, WebBrowserUtil.createParameterArray("-console", URL));
	}

	@Test
	public void testArraySubstitution() {
		assertArrayEquals(new String[] { "-url", URL, "-console" },
				WebBrowserUtil.createParameterArray("-url %URL% -console", URL));
	}

