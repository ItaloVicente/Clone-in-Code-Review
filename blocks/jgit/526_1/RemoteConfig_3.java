		if (!toAdd.equals(fetchUri)) {
			fetchUri = toAdd;
			return true;
		}

		return false;
	}

	public void setFetchURI(final URIish fetchUri) {
		this.fetchUri = fetchUri;
