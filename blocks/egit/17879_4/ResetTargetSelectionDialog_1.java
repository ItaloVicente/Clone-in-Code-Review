
	@Override
	protected boolean markRef(String refName) {
		return super
				.markRef(RepositoryUtil.isDetachedHead(repo) ? Constants.HEAD
						: refName);
	}
