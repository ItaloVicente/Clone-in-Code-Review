	private static String createUrl(IPath repoPath, String protocol, String user)
			throws URISyntaxException {
		URI uri = new URI(protocol, user, "localhost", 42, repoPath
				.setDevice(null).makeAbsolute().toString(), null, null);
		return uri.toString();
	}

