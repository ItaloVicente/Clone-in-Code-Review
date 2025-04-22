	@Override
	public AnyObjectId getCommitId() {
		throw new UnsupportedOperationException(
				NLS.bind(CoreText.BlobStorage_noCommit, blobId.name(), path));
	}

