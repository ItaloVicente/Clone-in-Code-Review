	public Charset getDefaultCharset() {
		Charset result = defaultCharset;
		if (result == null) {
			try {
				if (!StringUtils.isEmptyOrNull(encoding)) {
					result = Charset.forName(encoding);
				}
			} catch (IllegalCharsetNameException
					| UnsupportedCharsetException e) {
				LOG.error(JGitText.get().logInvalidDefaultCharset
			}
			if (result == null) {
				result = Charset.defaultCharset();
			}
			defaultCharset = result;
		}
		return result;
	}

