	private HttpURLConnection authorizeConnection(
			HttpURLConnection conn
			throws NoRemoteRepositoryException
			IOException {
		int status = HttpSupport.response(conn);
		switch (status) {
		case HttpURLConnection.HTTP_OK:
			return conn;

		case HttpURLConnection.HTTP_NOT_FOUND:
			throw new NoRemoteRepositoryException(uri
					JGitText.get().uriNotFound

		case HttpURLConnection.HTTP_UNAUTHORIZED:
			authMethod = HttpAuthMethod.scanResponse(conn);
			if (authMethod == HttpAuthMethod.NONE)
				throw new TransportException(uri
						JGitText.get().authenticationNotSupported
			if (1 < authAttempts
					|| !authMethod.authorize(uri
				throw new TransportException(uri
			return null;

		case HttpURLConnection.HTTP_FORBIDDEN:
			throw new TransportException(uri
					JGitText.get().serviceNotPermitted

		default:
			throw new TransportException(uri
		}
	}

