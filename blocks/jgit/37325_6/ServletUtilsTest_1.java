	@Test
	public void emptyContextPath() {
		assertEquals("/foo/bar"
				ServletUtils.getEncodedPathInfo(""
		assertEquals("/foo%2Fbar"
				ServletUtils.getEncodedPathInfo(""
	}

	public void slashContextPath() {
		assertEquals("/foo/bar"
				ServletUtils.getEncodedPathInfo("/"
		assertEquals("/foo%2Fbar"
				ServletUtils.getEncodedPathInfo("/"
	}

	@Test
	public void emptyServletPath() {
		assertEquals("/foo/bar"
				ServletUtils.getEncodedPathInfo("/c"
		assertEquals("/foo%2Fbar"
				ServletUtils.getEncodedPathInfo("/c"
	}

	@Test
	public void trailingSlashes() {
		assertEquals("/foo/bar/"
				ServletUtils.getEncodedPathInfo("/c"
		assertEquals("/foo/bar/"
				ServletUtils.getEncodedPathInfo("/c"
		assertEquals("/foo%2Fbar/"
				ServletUtils.getEncodedPathInfo("/c"
		assertEquals("/foo%2Fbar/"
	}

	@Test
	public void servletPathMatchesRequestPath() {
		assertEquals((String) null
				ServletUtils.getEncodedPathInfo("/c"
	}

