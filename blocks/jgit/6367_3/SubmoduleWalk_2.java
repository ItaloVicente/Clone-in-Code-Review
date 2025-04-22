	public SubmoduleWalk loadModulesConfig() throws IOException
		File modulesFile = new File(repository.getWorkTree()
				Constants.DOT_GIT_MODULES);
		FileBasedConfig config = new FileBasedConfig(modulesFile
				repository.getFS());
		config.load();
		modulesConfig = config;
		return this;
	}

	private void lazyLoadModulesConfig() throws IOException
		if (modulesConfig == null)
			loadModulesConfig();
	}

