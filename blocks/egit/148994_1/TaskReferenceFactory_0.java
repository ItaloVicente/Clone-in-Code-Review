	private TaskRepository getTaskRepository(Repository repository) {
		Config config = repository.getConfig();
		String url = config.getString(BUGTRACK_SECTION, null, BUGTRACK_URL);
		if (url != null) {
			return TasksUiPlugin.getRepositoryManager().getRepository(url);
		}
		url = config.getString(ConfigConstants.CONFIG_REMOTE_SECTION,
				Constants.DEFAULT_REMOTE_NAME, ConfigConstants.CONFIG_KEY_URL);
		if (url == null) {
