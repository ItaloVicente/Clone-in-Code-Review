	protected final void addHeaders(HttpConnection conn
		for (String header : headers) {
			String[] headerParts = header.split(":"
			if (headerParts.length != 2) {
				LOG.warn(MessageFormat.format(JGitText.get().invalidHeaderFormatFound
			} else {
				conn.setRequestProperty(headerParts[0]
			}
		}
	}

