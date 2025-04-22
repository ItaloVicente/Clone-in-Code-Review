					final int status = HttpSupport.response(conn);
					switch (status) {
					case HttpConnection.HTTP_OK:
						return;

					case HttpConnection.HTTP_NOT_FOUND:
						throw new NoRemoteRepositoryException(uri
								MessageFormat.format(JGitText.get().uriNotFound
										conn.getURL()));

					case HttpConnection.HTTP_FORBIDDEN:
