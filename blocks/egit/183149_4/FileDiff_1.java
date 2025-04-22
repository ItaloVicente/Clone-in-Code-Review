
	@Override
	public String getGitPath() {
		return getPath();
	}

	@Override
	public Source getSource() {
		return Source.COMMIT;
	}

	@Override
	public AnyObjectId getCommitId() {
		if (ChangeType.DELETE.equals(diffEntry.getChangeType())
				&& base != null) {
			return base.getId();
		}
		return commit.getId();
	}
