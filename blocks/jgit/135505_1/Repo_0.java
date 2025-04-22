	protected void run() {
		try {
			new RepoCommand(db)
				.setURI(uri)
				.setPath(path)
				.setGroups(groups)
				.call();
		} catch (GitAPIException e) {
			throw die(e.getMessage()
		}
