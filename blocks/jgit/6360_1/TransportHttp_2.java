	TransportHttp(final URIish uri) throws NotSupportedException {
		super(uri);
		try {
			String uriString = uri.toString();
			baseUrl = new URL(uriString);
			objectsUrl = new URL(baseUrl
		} catch (MalformedURLException e) {
			throw new NotSupportedException(MessageFormat.format(JGitText.get().invalidURL
		}
		http = new HttpConfig();
		proxySelector = ProxySelector.getDefault();
	}

