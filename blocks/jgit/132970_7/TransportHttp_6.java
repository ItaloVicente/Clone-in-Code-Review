	static void addHeaders(HttpConnection conn
		for (String header : headersToAdd) {
			int colon = header.indexOf(':');
			String key = null;
			if (colon > 0) {
				key = header.substring(0
			}
			if (key == null || key.isEmpty()) {
				LOG.warn(MessageFormat.format(
						JGitText.get().invalidHeaderFormat
			} else if (HttpSupport.scanToken(key
				LOG.warn(MessageFormat.format(JGitText.get().invalidHeaderKey
						header));
			} else {
				String value = header.substring(colon + 1).trim();
				if (!StandardCharsets.US_ASCII.newEncoder().canEncode(value)) {
					LOG.warn(MessageFormat
							.format(JGitText.get().invalidHeaderValue
				} else {
					conn.setRequestProperty(key
				}
			}
		}
	}

