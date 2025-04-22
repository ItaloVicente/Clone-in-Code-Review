		setURI(uri);
		http = local.getConfig().get(HTTP_KEY);
		proxySelector = ProxySelector.getDefault();
	}

	protected void setURI(final URIish uri) throws NotSupportedException {
