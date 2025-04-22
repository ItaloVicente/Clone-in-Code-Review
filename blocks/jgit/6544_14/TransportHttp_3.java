			if (handleAuth) {
				int authAttempts = 0;
				while (true) {
					HttpURLConnection newConn = authorizeConnection(
							conn
					if (newConn != null) {
						conn = newConn;
						break;
					}
					openStream();
					sendRequest();
				}
			} else {
				final int status = HttpSupport.response(conn);
				if (status != HttpURLConnection.HTTP_OK) {
					throw new TransportException(uri
							+ conn.getResponseMessage());
				}
