		URI u = server.getURI(false).resolve(path);
		return new URIish(u.toString());
	}

	protected URIish toURIish(String path
			throws URISyntaxException {
		URI u = server.getURI(ssl).resolve(path);
