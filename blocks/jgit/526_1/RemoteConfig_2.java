		List<URIish> result = new ArrayList<URIish>();
		if (fetchUri != null)
			result.add(fetchUri);
		return Collections.unmodifiableList(result);
	}

	public URIish getFetchUri() {
		return fetchUri;
