	private void abortWhenNotFound(URIish u
			throws NoRemoteRepositoryException {
		if (msg != null && !msg.isEmpty()) {
			msg = MessageFormat.format(JGitText.get().uriNotFoundWithMessage
					url
		} else {
			msg = MessageFormat.format(JGitText.get().uriNotFound
		}
		throw new NoRemoteRepositoryException(u
	}

