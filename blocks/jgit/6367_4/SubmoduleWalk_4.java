		return this;
	}

	private void lazyLoadModulesConfig() throws IOException
		if (modulesConfig == null)
			loadModulesConfig();
