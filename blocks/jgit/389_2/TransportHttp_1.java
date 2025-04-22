			int authAttempts = 1;
			for (;;) {
				final HttpURLConnection conn = httpOpen(u);
				if (useSmartHttp) {
					String exp = "application/x-" + service + "-advertisement";
					conn.setRequestProperty(HDR_ACCEPT
				} else {
					conn.setRequestProperty(HDR_ACCEPT
				}
				final int status = HttpSupport.response(conn);
				switch (status) {
				case HttpURLConnection.HTTP_OK:
					return conn;

				case HttpURLConnection.HTTP_NOT_FOUND:
					throw new NoRemoteRepositoryException(uri

				case HttpURLConnection.HTTP_UNAUTHORIZED:
					authMethod = HttpAuthMethod.scanResponse(conn);
					if (authMethod == HttpAuthMethod.NONE)
						throw new TransportException(uri
								"authentication not supported");
					if (1 < authAttempts || uri.getUser() == null)
						throw new TransportException(uri
					authMethod.authorize(uri);
					authAttempts++;
					continue;

				case HttpURLConnection.HTTP_FORBIDDEN:
					throw new TransportException(uri
							+ " not permitted");

				default:
					String err = status + " " + conn.getResponseMessage();
					throw new TransportException(uri
				}
