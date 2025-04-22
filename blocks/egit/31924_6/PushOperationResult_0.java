	public boolean isSuccessfulConnectionForAllURI() {
		for (final URIish uri : getURIs()) {
			if (!isSuccessfulConnection(uri))
				return false;
		}
		return true;
	}

