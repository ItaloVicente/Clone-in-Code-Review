	protected void run() throws Exception {
		new RepoCommand(db)
			.setURI(uri)
			.setPath(path)
			.setGroups(groups)
			.call();
