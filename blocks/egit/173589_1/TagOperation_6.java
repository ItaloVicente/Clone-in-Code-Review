	public String getMessage() {
		return message;
	}

	public TagOperation setTarget(@NonNull RevCommit target) {
		this.target = target;
		return this;
	}

	public RevCommit getTarget() {
		return target;
	}

	public TagOperation setTagger(PersonIdent tagger) {
		this.tagger = tagger;
		return this;
