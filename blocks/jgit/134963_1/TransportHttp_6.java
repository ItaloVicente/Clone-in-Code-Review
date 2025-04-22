		if (relevantCookies.size() > 0) {
			setCookieHeader(conn);
		}

		if (this.headers != null && !this.headers.isEmpty()) {
			for (Map.Entry<String
				conn.setRequestProperty(entry.getKey()
		}
		authMethod.configureRequest(conn);
		return conn;
	}

	private void setCookieHeader(HttpConnection conn) {
