				openStream();
				if (buf != out) {
					conn.setRequestProperty(HDR_CONTENT_ENCODING, ENCODING_GZIP);
				}
				conn.setFixedLengthStreamingMode((int) buf.length());
				try (OutputStream httpOut = conn.getOutputStream()) {
					buf.writeTo(httpOut, null);
				}

				final int status = HttpSupport.response(conn);
				switch (status) {
				case HttpConnection.HTTP_OK:
					return;

				case HttpConnection.HTTP_NOT_FOUND:
					throw new NoRemoteRepositoryException(uri, MessageFormat
							.format(JGitText.get().uriNotFound, conn.getURL()));

				case HttpConnection.HTTP_FORBIDDEN:
					throw new TransportException(uri,
							MessageFormat.format(
									JGitText.get().serviceNotPermitted,
									baseUrl, serviceName));

				case HttpConnection.HTTP_MOVED_PERM:
				case HttpConnection.HTTP_MOVED_TEMP:
				case HttpConnection.HTTP_11_MOVED_TEMP:
					if (http.getFollowRedirects() != HttpRedirectMode.TRUE) {
						return;
