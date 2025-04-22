		final String resolvedUri;
		try {
			resolvedUri = SubmoduleWalk.getSubmoduleRemoteUrl(repo
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
