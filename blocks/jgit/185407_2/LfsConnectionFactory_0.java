	private static void authorizeConnection(HttpConnection connection) {
		CredentialsProvider provider = LfsFactory.getCredentialsProvider();

		if (provider == null) {
			return;
		}

		CredentialItem.Username u = new CredentialItem.Username();
		CredentialItem.Password p = new CredentialItem.Password();

		final URIish uri = new URIish(connection.getURL());
		provider.get(uri
		provider.get(uri

		if (provider.supports(u
			String username;
			String password;
			username = u.getValue();
			char[] v = p.getValue();
			password = (v == null) ? null : new String(v);
			p.clear();

			String enc = Base64.encodeBytes(ident.getBytes(UTF_8));
			connection.setRequestProperty(HDR_AUTHORIZATION
		}
	}

