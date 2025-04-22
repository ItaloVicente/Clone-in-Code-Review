	private NoRemoteRepositoryException createNotFoundException(URIish u
			URL url
		String text;
		if (msg != null && !msg.isEmpty()) {
			text = MessageFormat.format(JGitText.get().uriNotFoundWithMessage
					url
		} else {
			text = MessageFormat.format(JGitText.get().uriNotFound
		}
		return new NoRemoteRepositoryException(u
	}

