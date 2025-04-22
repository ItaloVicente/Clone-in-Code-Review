			String defaultRepoDir = UIUtils.getDefaultRepositoryDir();
			File parentDir;
			if (defaultRepoDir.length() > 0)
				parentDir = new File(defaultRepoDir);
			else
				parentDir = ResourcesPlugin.getWorkspace().getRoot()
						.getRawLocation().toFile();
