	@Test
	public void testReadCookieFileWithMilliseconds() throws IOException {
		try (InputStream input = this.getClass()
				.getResourceAsStream("cookies-with-milliseconds.txt")) {
			Files.copy(input
		}
		NetscapeCookieFile cookieFile = new NetscapeCookieFile(tmpFile
				TEST_DATE);
		long expectedMaxAge = Duration.between(TEST_DATE
				.getSeconds();
		for (HttpCookie cookie : cookieFile.getCookies(true)) {
			assertEquals(expectedMaxAge
		}
	}

