				final int status = HttpSupport.response(conn);
				switch (status) {
				case HttpURLConnection.HTTP_OK:
					return conn;

				case HttpURLConnection.HTTP_NOT_FOUND:
					throw new NoRemoteRepositoryException(uri,
							MessageFormat.format(JGitText.get().uriNotFound, u));

				case HttpURLConnection.HTTP_UNAUTHORIZED:
					authMethod = HttpAuthMethod.scanResponse(conn);
					if (authMethod == HttpAuthMethod.NONE)
						throw new TransportException(uri, MessageFormat.format(
								JGitText.get().authenticationNotSupported, uri));
					if (1 < authAttempts
							|| !authMethod.authorize(uri,
									getCredentialsProvider())) {
						throw new TransportException(uri,
								JGitText.get().notAuthorized);
					}
					authAttempts++;
					continue;

				case HttpURLConnection.HTTP_FORBIDDEN:
					throw new TransportException(uri, MessageFormat.format(
							JGitText.get().serviceNotPermitted, service));

				default:
					throw new TransportException(uri, err);
				}
