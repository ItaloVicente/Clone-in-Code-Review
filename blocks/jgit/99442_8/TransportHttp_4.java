				final int status = HttpSupport.response(conn);
				switch (status) {
				case HttpConnection.HTTP_OK:
					return;

				case HttpConnection.HTTP_NOT_FOUND:
					throw new NoRemoteRepositoryException(uri
							.format(JGitText.get().uriNotFound

				case HttpConnection.HTTP_FORBIDDEN:
					throw new TransportException(uri
							MessageFormat.format(
									JGitText.get().serviceNotPermitted
									baseUrl

				case HttpConnection.HTTP_MOVED_PERM:
				case HttpConnection.HTTP_MOVED_TEMP:
				case HttpConnection.HTTP_11_MOVED_TEMP:
					if (http.followRedirects != HttpRedirectMode.TRUE) {
						return;
					}
					URIish newUri = redirect(
							conn.getHeaderField(HDR_LOCATION)
							'/' + serviceName
					try {
						baseUrl = toURL(newUri);
					} catch (MalformedURLException e) {
						throw new TransportException(uri
								JGitText.get().invalidRedirectLocation
								baseUrl
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
						if (authenticator != null) {
							ignoreTypes.add(authenticator.getType());
