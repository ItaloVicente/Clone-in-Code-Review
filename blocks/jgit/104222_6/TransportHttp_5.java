						currentUri = redirect(conn.getHeaderField(HDR_LOCATION)
								'/' + serviceName
						try {
							baseUrl = toURL(currentUri);
						} catch (MalformedURLException e) {
							throw new TransportException(uri
									MessageFormat.format(
											JGitText.get().invalidRedirectLocation
											baseUrl
									e);
						}
						continue;

					case HttpConnection.HTTP_UNAUTHORIZED:
						HttpAuthMethod nextMethod = HttpAuthMethod
								.scanResponse(conn
						switch (nextMethod.getType()) {
						case NONE:
							throw new TransportException(uri
									MessageFormat.format(
											JGitText.get().authenticationNotSupported
											conn.getURL()));
						case NEGOTIATE:
							ignoreTypes.add(HttpAuthMethod.Type.NEGOTIATE);
