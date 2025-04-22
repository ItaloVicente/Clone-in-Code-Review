	private boolean isHttpProtocol(URIish uri) {
		return Protocol.HTTP.handles(uri) || Protocol.HTTPS.handles(uri);
	}

	/**
	 * @param u
	 * @return URI with path prefixed for Gerrit smart HTTP support
	 */
	private URIish prependGerritHttpPathPrefix(URIish u) {
		String path = u.getPath();
		if (!path.startsWith(GERRIT_HTTP_PATH_PREFIX))
			return u.setPath(GERRIT_HTTP_PATH_PREFIX + path);
		return u;
	}

	/**
	 * @param u
	 * @return URI without Gerrit smart HTTP path prefix
	 */
	private URIish removeGerritHttpPathPrefix(URIish u) {
		String path = u.getPath();
		if (path.startsWith(GERRIT_HTTP_PATH_PREFIX))
			return u.setPath(path.substring(4));
		return u;
	}

