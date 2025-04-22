
			final int status = HttpSupport.response(conn);
			if (status == HttpConnection.HTTP_MOVED_PERM) {
				String locationHeader = HttpSupport.responseHeader(conn
				sendRequest(locationHeader);
			}
