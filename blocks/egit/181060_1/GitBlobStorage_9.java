	@Override
	public Repository getRepository() {
		return db;
	}

	@Override
	public String getGitPath() {
		return path;
	}

	@Override
	public AnyObjectId getCommitId() {
		return null;
	}

	@Override
	public Source getSource() {
		return null;
	}

