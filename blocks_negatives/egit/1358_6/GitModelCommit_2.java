	protected String getBaseSha1() {
		return baseCommit.getId().getName();
	}

	/**
	 * @return SHA1 of remote object
	 */
	protected String getRemoteSha1() {
		return remoteCommit.getId().getName();
