		this.update = update;
		this.uri = uri;
		this.reader = reader;
		this.repo = repo;
		String remote = update.getRemoteName();
		tag = remote != null && remote.startsWith(Constants.R_TAGS);
	}

	URIish getUri() {
		return uri;
