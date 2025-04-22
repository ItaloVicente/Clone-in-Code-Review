	private Map<String, StageState> getFiles()
			throws RevisionSyntaxException, NoWorkTreeException,
			GitAPIException {
		Map<String, StageState> files = new TreeMap<>();
		try (Git git = new Git(db)) {
			StatusCommand statusCommand = git.status();
			if (filterPaths != null && filterPaths.size() > 0) {
				for (String path : filterPaths) {
					statusCommand.addPath(path);
