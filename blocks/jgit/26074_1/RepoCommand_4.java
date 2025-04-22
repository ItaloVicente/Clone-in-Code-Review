	public RepoCommand setAuthor(final PersonIdent author) {
		this.author = author;
		return this;
	}

	public RepoCommand setGetHeadFromUri(final GetHeadFromUri callback) {
		this.callback = callback;
		return this;
	}

