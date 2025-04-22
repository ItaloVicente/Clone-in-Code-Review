		if (rootTree == null) {
			File modulesFile = new File(repository.getWorkTree()
					Constants.DOT_GIT_MODULES);
			FileBasedConfig config = new FileBasedConfig(modulesFile
					repository.getFS());
			config.load();
			modulesConfig = config;
		} else {
