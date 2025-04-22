	private void addHeaders(HttpConnection conn
		for (String header : headersToAdd) {
			int colon = header.indexOf(':');
			String key = null;
			if (colon > 0) {
				key = header.substring(0
			}
			if (StringUtils.isEmptyOrNull(key)) {
				LOG.warn(MessageFormat.format(
						JGitText.get().invalidHeaderFormatFound
			} else {
				conn.setRequestProperty(key
						header.substring(colon + 1).trim());
			}
		}
	}

