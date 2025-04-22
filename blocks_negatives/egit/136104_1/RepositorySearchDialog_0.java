		String defaultRepoPath = RepositoryUtil.getDefaultRepositoryDir();

		String initialPath = prefs.get(PREF_PATH, defaultRepoPath);

		dir.setText(initialPath);

