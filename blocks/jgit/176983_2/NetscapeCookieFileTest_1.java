	@Test
	public void testReadCookieFileWithMilliseconds() throws IOException {
		try (InputStream input = this.getClass()
				.getResourceAsStream("cookies-with-milliseconds.txt")) {
			Files.copy(input
		}
		NetscapeCookieFile cookieFile = new NetscapeCookieFile(tmpFile
				NOW);
		long expectedMaxAge = JAN_01_2030_NOON
				- TimeUnit.MILLISECONDS.toSeconds(NOW.getTime());
		for (HttpCookie cookie : cookieFile.getCookies(true)) {
			assertEquals(expectedMaxAge
		}
	}

