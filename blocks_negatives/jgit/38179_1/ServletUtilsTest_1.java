	@Test
	public void emptyContextPath() {
		assertEquals("/foo/bar",
				ServletUtils.getEncodedPathInfo("", "/s", "/s/foo/bar"));
		assertEquals("/foo%2Fbar",
				ServletUtils.getEncodedPathInfo("", "/s", "/s/foo%2Fbar"));
	}

	public void slashContextPath() {
		assertEquals("/foo/bar",
				ServletUtils.getEncodedPathInfo("/", "/s", "/s/foo/bar"));
		assertEquals("/foo%2Fbar",
				ServletUtils.getEncodedPathInfo("/", "/s", "/s/foo%2Fbar"));
	}

	@Test
	public void emptyServletPath() {
		assertEquals("/foo/bar",
				ServletUtils.getEncodedPathInfo("/c", "", "/c/foo/bar"));
		assertEquals("/foo%2Fbar",
				ServletUtils.getEncodedPathInfo("/c", "", "/c/foo%2Fbar"));
	}

	@Test
	public void trailingSlashes() {
		assertEquals("/foo/bar/",
				ServletUtils.getEncodedPathInfo("/c", "/s", "/c/s/foo/bar/"));
		assertEquals("/foo/bar/",
				ServletUtils.getEncodedPathInfo("/c", "/s", "/c/s/foo/bar///"));
		assertEquals("/foo%2Fbar/",
				ServletUtils.getEncodedPathInfo("/c", "/s", "/c/s/foo%2Fbar/"));
		assertEquals("/foo%2Fbar/", ServletUtils.getEncodedPathInfo("/c", "/s",
	}

	@Test
	public void servletPathMatchesRequestPath() {
		assertEquals((String) null,
				ServletUtils.getEncodedPathInfo("/c", "/s", "/c/s"));
	}

