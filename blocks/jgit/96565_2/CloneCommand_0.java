		URIish u = null;
		try {
			u = new URIish(uri);
			verifyDirectories(u);
		} catch (URISyntaxException e) {
			throw new InvalidRemoteException(
					MessageFormat.format(JGitText.get().invalidURL
		}
