
	public List<URIish> getAllUris() {
		if (myFetchMode) {
			throw new IllegalStateException();
		}
		List<URIish> uris = new ArrayList<URIish>();
		uris.addAll(myUris);
		if (uris.isEmpty()) {
			uris.add(myUri);
		}
		return uris;
	}
