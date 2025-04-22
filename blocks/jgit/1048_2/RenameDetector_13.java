	public void setRenameScore(int score) {
		if (score < 0 || score > 100)
			throw new IllegalArgumentException(
					JGitText.get().similarityScoreMustBeWithinBounds);
		renameScore = score;
	}

	public int getRenameLimit() {
		return renameLimit;
	}

	public void setRenameLimit(int limit) {
		renameLimit = limit;
	}

	public boolean isOverRenameLimit() {
		if (done)
			return overRenameLimit;
		int cnt = Math.max(added.size()
		return getRenameLimit() != 0 && getRenameLimit() < cnt;
	}

