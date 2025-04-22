				TransportHttp.matchesCookiePath("otherpath"
	}

	@Test
	public void testProcessResponseCookies() throws IOException {
		HttpConnection connection = Mockito.mock(HttpConnection.class);
		Mockito.when(
				connection.getHeaderFields(ArgumentMatchers.eq("Set-Cookie")))
				.thenReturn(Arrays.asList(
						"id=a3fWa; Expires=Fri
						"sessionid=38afes7a8; HttpOnly; Path=/"));
		Mockito.when(
				connection.getHeaderFields(ArgumentMatchers.eq("Set-Cookie2")))
				.thenReturn(Collections
						.singletonList("cookie2=some value; Max-Age=1234; Path=/"));

		try (TransportHttp transportHttp = new TransportHttp(db
			Date creationDate = new Date();
			transportHttp.processResponseCookies(connection);

			Set<HttpCookie> expectedCookies = new LinkedHashSet<>();

			HttpCookie cookie = new HttpCookie("id"
			cookie.setDomain("everyones.loves.git");
			cookie.setPath("/u/2/");

			cookie.setMaxAge(
					(Instant.parse("2100-01-01T11:00:00.000Z").toEpochMilli()
							- creationDate.getTime()) / 1000);
			cookie.setSecure(true);
			cookie.setHttpOnly(true);
			expectedCookies.add(cookie);

			cookie = new HttpCookie("cookie2"
			cookie.setDomain("everyones.loves.git");
			cookie.setPath("/");
			cookie.setMaxAge(1234);
			expectedCookies.add(cookie);

			Assert.assertThat(
					new NetscapeCookieFile(cookieFile.toPath())
							.getCookies(true)
					HttpCookiesMatcher.containsInOrder(expectedCookies
		}
	}

	@Test
	public void testProcessResponseCookiesNotPersistingWithSaveCookiesFalse()
			throws IOException {
		HttpConnection connection = Mockito.mock(HttpConnection.class);
		Mockito.when(
				connection.getHeaderFields(ArgumentMatchers.eq("Set-Cookie")))
				.thenReturn(Arrays.asList(
						"id=a3fWa; Expires=Thu
						"sessionid=38afes7a8; HttpOnly; Path=/"));
		Mockito.when(
				connection.getHeaderFields(ArgumentMatchers.eq("Set-Cookie2")))
				.thenReturn(Collections.singletonList(
						"cookie2=some value; Max-Age=1234; Path=/"));

		final Config config = db.getConfig();
		config.setBoolean("http"

		try (TransportHttp transportHttp = new TransportHttp(db
			transportHttp.processResponseCookies(connection);

			Assert.assertFalse("Cookie file was not supposed to be written!"
					cookieFile.exists());
		}
