	protected Transport(final URIish uri) {
		this.uri = uri;
		this.local = null;
		this.checkFetchedObjects = true;
		this.credentialsProvider = CredentialsProvider.getDefault();
	}

