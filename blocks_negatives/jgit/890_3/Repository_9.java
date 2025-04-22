	public GitIndex getIndex() throws IOException, IllegalStateException {
		if (isBare())
			throw new IllegalStateException(
					JGitText.get().bareRepositoryNoWorkdirAndIndex);
		if (index == null) {
			index = new GitIndex(this);
			index.read();
		} else {
			index.rereadIfNecessary();
		}
		return index;
	}
