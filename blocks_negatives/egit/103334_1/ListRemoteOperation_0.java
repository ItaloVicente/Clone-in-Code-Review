	public ListRemoteOperation(final Repository localDb, final URIish uri,
			int timeout) {
		try (Git git = new Git(localDb)) {
			rc = git.lsRemote();
			rc.setRemote(uri.toString()).setTimeout(timeout);
		}
