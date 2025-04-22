
	public List<URIish> getAllUris() {
		if (myFetchMode) {
			throw new IllegalStateException();
		}
		List<URIish> uris = new ArrayList<URIish>();
		uris.addAll(myUris);
		if (myUri != null) {
			uris.remove(myUri);
			uris.add(0, myUri);
		}
		return uris;
	}
