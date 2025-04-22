	@Override
	public IStorage getStorage(IProgressMonitor monitor) throws TeamException {
		return new CommitBlobStorage(getRepo(), getCachePath(), getObjectId(),
				getCommit());
	}

