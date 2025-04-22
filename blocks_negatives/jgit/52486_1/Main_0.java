		final String userpass = u.getUserInfo();
			final int c = userpass.indexOf(':');
			final String user = userpass.substring(0, c);
			final String pass = userpass.substring(c + 1);
			CachedAuthenticator
					.add(new CachedAuthenticator.CachedAuthentication(
							proxyHost, proxyPort, user, pass));
