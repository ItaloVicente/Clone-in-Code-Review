	public CommitCommand setCleanupMode(@NonNull CleanupMode mode) {
		checkCallable();
		this.cleanupMode = mode;
		return this;
	}

	public CommitCommand setDefaultClean(boolean strip) {
		checkCallable();
		this.cleanDefaultIsStrip = strip;
		return this;
	}

	public CommitCommand setCommentCharacter(Character commentChar) {
		checkCallable();
		this.commentChar = commentChar;
		return this;
	}

