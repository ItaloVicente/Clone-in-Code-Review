	public RemoteRefUpdate(final Repository localDb, final String srcRef,
			final ObjectId srcId, final String remoteName,
			final boolean forceUpdate, final String localName,
			final ObjectId expectedOldObjectId) throws IOException {
		if (remoteName == null)
			throw new IllegalArgumentException(JGitText.get().remoteNameCannotBeNull);
		if (srcId == null && srcRef != null)
			throw new IOException(MessageFormat.format(
					JGitText.get().sourceRefDoesntResolveToAnyObject, srcRef));

		if (srcRef != null)
