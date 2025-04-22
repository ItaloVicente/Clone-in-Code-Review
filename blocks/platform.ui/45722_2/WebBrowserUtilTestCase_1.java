	private static final String URL = "http://127.0.0.1:3873/help/index.jsp";

	public void testNullParameters() {
		assertEquals(URL, WebBrowserUtil.createParameterString(null, URL));
	}

	public void testEmptyParameters() {
		assertEquals(URL, WebBrowserUtil.createParameterString("", URL));
	}

	public void testNullURL() {
		assertEquals("", WebBrowserUtil.createParameterString("", null));
	}

	public void testNoSubstitution() {
		assertEquals("-console " + URL, WebBrowserUtil.createParameterString("-console", URL));
	}

	public void testSubstitution() {
		assertEquals("-url " + URL + " -console", WebBrowserUtil.createParameterString("-url %URL% -console", URL));
	}

	private void assertArrayEquals(String[] a1, String[] a2) {
		assertEquals("Arrays have different lengths", a1.length, a2.length);
		for (int i = 0; i < a1.length; i++) {
			assertEquals("Elements differ at index " + i, a1[i], a2[i]);
		}
	}

	public void testArrayNullParameters() {
		assertArrayEquals(new String[] { URL }, WebBrowserUtil.createParameterArray(null, URL));
	}

	public void testArrayEmptyParameters() {
		assertArrayEquals(new String[] { URL }, WebBrowserUtil.createParameterArray("", URL));
	}

	public void testArrayNullURL() {
		assertArrayEquals(new String[0], WebBrowserUtil.createParameterArray("", null));
	}

	public void testArrayNoSubstitution() {
		assertArrayEquals(new String[] { "-console", URL }, WebBrowserUtil.createParameterArray("-console", URL));
	}

	public void testArraySubstitution() {
		assertArrayEquals(new String[] { "-url", URL, "-console" },
				WebBrowserUtil.createParameterArray("-url %URL% -console", URL));
	}

