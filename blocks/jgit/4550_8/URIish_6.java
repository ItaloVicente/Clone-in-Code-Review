		r.rawPath = n;
		return r;
	}

	public URIish setRawPath(final String n) throws URISyntaxException {
		final URIish r = new URIish(this);
		r.path = unescape(n);
		r.rawPath = n;
