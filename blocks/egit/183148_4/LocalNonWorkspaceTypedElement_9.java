
	@Override
	public Repository getRepository() {
		return repository;
	}

	@Override
	public String getGitPath() {
		IPath relativePath = ResourceUtil.getRepositoryRelativePath(path,
				repository);
		return relativePath == null ? null : relativePath.toPortableString();
	}

	@Override
	public Source getSource() {
		return Source.WORKING_TREE;
	}

	@Override
	public AnyObjectId getCommitId() {
		return null;
	}
