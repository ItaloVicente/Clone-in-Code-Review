	@Override
	protected String getAncestorSha1() {
		return ancestorId.getName();
	}

	@Override
	protected String getBaseSha1() {
		return baseId.getName();
	}

	@Override
	protected String getRemoteSha1() {
		return remoteId.getName();
	}

