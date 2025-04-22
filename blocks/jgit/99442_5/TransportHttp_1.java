				final int status = HttpSupport.response(conn);
				switch (status) {
				case HttpConnection.HTTP_MOVED_PERM:
					if (3 < redirects++) {
						throw new TransportException(uri
								MessageFormat.format(
										JGitText.get().tooManyRedirects
										Integer.valueOf(3)
					}
					String redirectUrl = HttpSupport.responseHeader(conn
							HDR_LOCATION);
					try {
						requestUrl = new URL(redirectUrl);
					} catch (MalformedURLException e) {
						throw new TransportException(e.getLocalizedMessage()
								e);
					}
					continue;

				case HttpConnection.HTTP_OK:
					return;

				case HttpConnection.HTTP_NOT_FOUND:
					throw new NoRemoteRepositoryException(uri
							.format(JGitText.get().uriNotFound

				case HttpConnection.HTTP_UNAUTHORIZED:
					int newAuthAttempts = authAttempts;
					boolean rescan;
					HttpAuthMethod nextMethod = null;
					do {
						rescan = false;
						nextMethod = HttpAuthMethod.scanResponse(conn
								ignoreTypes);
						switch (nextMethod.getType()) {
						case NONE:
							throw new TransportException(uri
									MessageFormat.format(
											JGitText.get().authenticationNotSupported
											requestUrl));
						case NEGOTIATE:
							if (authenticator == null || authenticator
									.getType() != nextMethod.getType()) {
								if (authenticator != null) {
									ignoreTypes.add(authenticator.getType());
								}
								authAttempts = 1;
								newAuthAttempts = 2;
							} else {
								nextMethod = authenticator;
								if (++negotiationRounds > 10) {
									ignoreTypes
											.add(HttpAuthMethod.Type.NEGOTIATE);
									rescan = true;
								}
							}
							break;
						default:
							ignoreTypes.add(HttpAuthMethod.Type.NEGOTIATE);
							if (authenticator == null || authenticator
									.getType() != nextMethod.getType()) {
								if (authenticator != null) {
									ignoreTypes.add(authenticator.getType());
								}
								authAttempts = 1;
								newAuthAttempts = 2;
							} else {
								newAuthAttempts++;
							}
							break;
						}
					} while (rescan);
					authMethod = nextMethod;
					authenticator = nextMethod;
					URIish requestUri = new URIish(requestUrl);
					CredentialsProvider credentialsProvider = getCredentialsProvider();
					if (credentialsProvider == null) {
						throw new TransportException(requestUri
								JGitText.get().noCredentialsProvider);
					}
					if (authAttempts > 1) {
						credentialsProvider.reset(requestUri);
					}
					if (3 < authAttempts || !authMethod.authorize(requestUri
							credentialsProvider)) {
						throw new TransportException(requestUri
								JGitText.get().notAuthorized);
					}
					authAttempts = newAuthAttempts;
					continue;

				case HttpConnection.HTTP_FORBIDDEN:
					throw new TransportException(new URIish(requestUrl)
							MessageFormat.format(
									JGitText.get().serviceNotPermitted
									serviceName));

				default:
					String err = status + ' ' + requestUrl.toString() + ' '
							+ conn.getResponseMessage();
					throw new TransportException(uri
				}
