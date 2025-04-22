	public static int response(final java.net.HttpURLConnection c)
			throws IOException {
		try {
			return c.getResponseCode();
		} catch (ConnectException ce) {
			final String host = c.getURL().getHost();
			if ("Connection timed out: connect".equals(ce.getMessage()))
				throw new ConnectException(MessageFormat.format(
						JGitText.get().connectionTimeOut
		}
	}

