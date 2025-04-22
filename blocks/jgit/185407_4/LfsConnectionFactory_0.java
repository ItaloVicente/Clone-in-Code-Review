	private static void authorizeFromCredentialsProvider(HttpConnection connection) {
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

			authorizeConnection(connection
		}
	}

	private static boolean authorizeFromUri(HttpConnection connection
		try {
			URIish uri = new URIish(lfsUrl);
			String user = uri.getUser();
			String pass = uri.getPass();

			if (user != null && pass != null) {
				user = URLDecoder.decode(user.replace("+"
						StandardCharsets.UTF_8.name());
				pass = URLDecoder.decode(pass.replace("+"
						StandardCharsets.UTF_8.name());

				authorizeConnection(connection

				return true;
			}
		} catch (URISyntaxException | UnsupportedEncodingException e) {
			LOG.warn(JGitText.get().httpUserInfoDecodeError
		}

		return false;
	}

	private static void authorizeConnection(HttpConnection connection
		String enc = Base64.encodeBytes(ident.getBytes(UTF_8));
		connection.setRequestProperty(HDR_AUTHORIZATION
	}

