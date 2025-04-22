	@Test
	public void emptyContextPath() {
		assertEquals("/foo/bar"
				ServletUtils.getEncodedPathInfo("/s"
		assertEquals("/foo%2Fbar"
				ServletUtils.getEncodedPathInfo("/s"
	}

	@Test
	public void emptyServletPath() {
		assertEquals("/foo/bar"
				ServletUtils.getEncodedPathInfo(""
		assertEquals("/foo%2Fbar"
				ServletUtils.getEncodedPathInfo(""
	}

	@Test
	public void trailingSlashes() {
		assertEquals("/foo/bar/"
				ServletUtils.getEncodedPathInfo("/s"
		assertEquals("/foo/bar/"
				ServletUtils.getEncodedPathInfo("/s"
		assertEquals("/foo%2Fbar/"
				ServletUtils.getEncodedPathInfo("/s"
		assertEquals("/foo%2Fbar/"
	}

	@Test
	public void servletPathMatchesRequestPath() {
		assertEquals(null
	}

