	public RemoteRefUpdate(Repository localDb
			String remoteName
			ObjectId expectedOldObjectId) throws IOException {
		this(localDb
				expectedOldObjectId);
	}

	private RemoteRefUpdate(Repository localDb
			String remoteName
			Collection<RefSpec> fetchSpecs
			throws IOException {
		if (fetchSpecs == null) {
			if (remoteName == null) {
				throw new IllegalArgumentException(
						JGitText.get().remoteNameCannotBeNull);
			}
			if (srcId == null && srcRef != null) {
				throw new IOException(MessageFormat.format(
						JGitText.get().sourceRefDoesntResolveToAnyObject
						srcRef));
			}
		}
		if (srcRef != null) {
