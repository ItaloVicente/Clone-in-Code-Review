
	private static void configureHttpProxy() throws MalformedURLException {
		final String s = System.getenv("http_proxy");
		if (s == null || s.equals(""))
			return;

		if (!"http".equals(u.getProtocol()))
			throw new MalformedURLException("Invalid http_proxy: " + s
					+ ": Only http supported.");

		final String proxyHost = u.getHost();
		final int proxyPort = u.getPort();

		System.setProperty("http.proxyHost"
		if (proxyPort > 0)
			System.setProperty("http.proxyPort"

		final String userpass = u.getUserInfo();
		if (userpass != null && userpass.contains(":")) {
			final int c = userpass.indexOf(':');
			final String user = userpass.substring(0
			final String pass = userpass.substring(c + 1);
			AwtAuthenticator.add(new AwtAuthenticator.CachedAuthentication(
					proxyHost
		}
	}
