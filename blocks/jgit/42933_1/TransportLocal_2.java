	private Repository openRepo() throws TransportException {
		try {
			return new RepositoryBuilder().setGitDir(remoteGitDir).build();
		} catch (IOException err) {
			throw new TransportException(uri
		}
	}

