			final HttpURLConnection conn = httpOpen(u);
			if (useSmartHttp) {
				String expType = "application/x-" + service + "-advertisement";
				conn.setRequestProperty(HDR_ACCEPT, expType + ", */*");
			} else {
				conn.setRequestProperty(HDR_ACCEPT, "*/*");
			}
			final int status = HttpSupport.response(conn);
			switch (status) {
			case HttpURLConnection.HTTP_OK:
				return conn;

			case HttpURLConnection.HTTP_NOT_FOUND:
				throw new NoRemoteRepositoryException(uri, u + " not found");

			case HttpURLConnection.HTTP_FORBIDDEN:
				throw new TransportException(uri, service + " not permitted");

			default:
				String err = status + " " + conn.getResponseMessage();
				throw new TransportException(uri, err);
