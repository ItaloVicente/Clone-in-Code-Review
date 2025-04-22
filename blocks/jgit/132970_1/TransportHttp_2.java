	static final void addHeaders(HttpConnection conn
		for (String header : headers) {
			String[] headerParts = header.split(":"
			if (headerParts.length != 2) {
				LOG.warn(
						"Invalid header format found: No separator ':' found in header '{}'
						header);
			} else {
				conn.setRequestProperty(headerParts[0]
			}
		}
	}

