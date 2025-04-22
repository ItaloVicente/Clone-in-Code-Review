	/**
	 * Configure the JRE's standard HTTP based on <code>http_proxy</code>.
	 * <p>
	 * The popular libcurl library honors the <code>http_proxy</code>
	 * environment variable as a means of specifying an HTTP proxy for requests
	 * made behind a firewall. This is not natively recognized by the JRE, so
	 * this method can be used by command line utilities to configure the JRE
	 * before the first request is sent.
	 *
	 * @throws MalformedURLException
	 *             the value in <code>http_proxy</code> is unsupportable.
	 */
	public static void configureHttpProxy() throws MalformedURLException {
		final String s = System.getenv("http_proxy");
		if (s == null || s.equals(""))
			return;

		if (!"http".equals(u.getProtocol()))
			throw new MalformedURLException("Invalid http_proxy: " + s
					+ ": Only http supported.");

		final String proxyHost = u.getHost();
		final int proxyPort = u.getPort();

		System.setProperty("http.proxyHost", proxyHost);
		if (proxyPort > 0)
			System.setProperty("http.proxyPort", String.valueOf(proxyPort));

		final String userpass = u.getUserInfo();
		if (userpass != null && userpass.contains(":")) {
			final int c = userpass.indexOf(':');
			final String user = userpass.substring(0, c);
			final String pass = userpass.substring(c + 1);
			AwtAuthenticator.add(new AwtAuthenticator.CachedAuthentication(
					proxyHost, proxyPort, user, pass));
		}
	}

