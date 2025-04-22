		MockSystemReader reader = new MockSystemReader();
		reader.setProperty(Constants.GIT_AUTHOR_NAME_KEY, null);
		reader.setProperty(Constants.GIT_AUTHOR_EMAIL_KEY, null);
		reader.setProperty(Constants.GIT_COMMITTER_NAME_KEY, null);
		reader.setProperty(Constants.GIT_COMMITTER_EMAIL_KEY, null);
		reader.setProperty(Constants.GIT_CONFIG_NOSYSTEM_KEY, null);
		reader.setProperty(Constants.GIT_CEILING_DIRECTORIES_KEY, null);
		reader.setProperty(Constants.GIT_DIR_KEY, null);
		reader.setProperty(Constants.GIT_WORK_TREE_KEY, null);
		reader.setProperty(Constants.GIT_INDEX_FILE_KEY, null);
		reader.setProperty(Constants.GIT_OBJECT_DIRECTORY_KEY, null);
		reader.setProperty(Constants.GIT_ALTERNATE_OBJECT_DIRECTORIES_KEY,
				null);
		SystemReader.setInstance(reader);
