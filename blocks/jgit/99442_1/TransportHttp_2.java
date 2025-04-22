				final int status = HttpSupport.response(conn);
				System.out.println("Response code: " + status);
				switch (status) {
				case HttpConnection.HTTP_MOVED_PERM:
					if (3 < redirects++) {
						throw new TransportException(uri
					}
					String redirectUrl = HttpSupport.responseHeader(conn
							HDR_LOCATION);
					requestUrl = new URL(redirectUrl);
					continue;

				case HttpConnection.HTTP_OK:
					return;

				case HttpConnection.HTTP_NOT_FOUND:
					throw new NoRemoteRepositoryException(uri
							.format(JGitText.get().uriNotFound

				case HttpConnection.HTTP_UNAUTHORIZED:
					if (authenticator != null && authenticator
							.getType() != HttpAuthMethod.Type.NEGOTIATE) {
						ignoreTypes.add(authenticator.getType());
					}
					int newAuthAttempts = authAttempts;
					boolean rescan = false;
					HttpAuthMethod nextMethod = null;
					do {
						nextMethod = HttpAuthMethod.scanResponse(conn
								ignoreTypes);
						System.out.println("AuthMethod:"
								+ nextMethod.getType().getSchemeName());
						switch (nextMethod.getType()) {
						case NONE:
							throw new TransportException(uri
									MessageFormat.format(
											JGitText.get().authenticationNotSupported
											requestUrl));
						case NEGOTIATE:
							if (authenticator != null && authenticator
									.getType() == HttpAuthMethod.Type.NEGOTIATE) {
								nextMethod = authenticator;
								if (++negotiationRounds > 10) {
									ignoreTypes
											.add(HttpAuthMethod.Type.NEGOTIATE);
									rescan = true;
								}
							} else {
								authAttempts = 0;
								newAuthAttempts = 1;
							}
							break;
						default:
							ignoreTypes.add(HttpAuthMethod.Type.NEGOTIATE);
							if (authenticator == null || authenticator
									.getType() != nextMethod.getType()) {
								authAttempts = 0;
								newAuthAttempts = 1;
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
					if (authAttempts > 0) {
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

				default:
					String err = status + ' ' + requestUrl.toString() + ' '
							+ conn.getResponseMessage();
					throw new TransportException(uri
				}
