	public RepoCommand setAuthor(final PersonIdent author) {
		this.author = author;
		return this;
	}

	public RepoCommand setRemoteReader(final RemoteReader callback) {
		this.callback = callback;
		return this;
	}

