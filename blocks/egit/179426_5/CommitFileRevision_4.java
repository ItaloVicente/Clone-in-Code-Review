	@Override
	public Source getSource() {
		return Source.COMMIT;
	}

	@Override
	public AnyObjectId getCommitId() {
		return getRevCommit();
	}

