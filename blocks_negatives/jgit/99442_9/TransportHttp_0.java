				if (http.followRedirects == HttpRedirectMode.TRUE) {
					final int status = HttpSupport.response(conn);
					switch (status) {
					case HttpConnection.HTTP_MOVED_PERM:
					case HttpConnection.HTTP_MOVED_TEMP:
					case HttpConnection.HTTP_11_MOVED_TEMP:
						URIish newUri = redirect(
								conn.getHeaderField(HDR_LOCATION),
								'/' + serviceName, redirects++);
						try {
							baseUrl = toURL(newUri);
						} catch (MalformedURLException e) {
							throw new TransportException(MessageFormat.format(
									JGitText.get().invalidRedirectLocation,
									uri, baseUrl, newUri), e);
