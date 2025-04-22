		String initialDirectory = RepositoryUtil.getDefaultRepositoryDir();
		int cursorPosition = initialDirectory.length();
		if (!initialDirectory.isEmpty()) {
			initialDirectory = RepositoryUtil.getDefaultRepositoryDir()
					+ File.separatorChar
					+ UIText.CreateRepositoryPage_DefaultRepositoryName;
			int repoCounter = 2;
			while (Paths.get(initialDirectory).toFile().exists()) {
				initialDirectory = RepositoryUtil.getDefaultRepositoryDir()
						+ File.separatorChar
						+ UIText.CreateRepositoryPage_DefaultRepositoryName + repoCounter++;
			}
			cursorPosition++;
		}
		directoryText.setText(initialDirectory);
