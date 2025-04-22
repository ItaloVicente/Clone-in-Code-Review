	public Properties getStatistics() throws GitAPIException {
		try {
			GC gc = new GC((FileRepository) repo);
			return toProperties(gc.getStatistics());
		} catch (IOException e) {
			throw new JGitInternalException(
					JGitText.get().couldNotGetRepoStatistics
		}
	}

