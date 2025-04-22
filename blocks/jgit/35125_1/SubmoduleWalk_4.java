	public IgnoreSubmoduleMode getModulesIgnore() throws IOException
			ConfigInvalidException {
		lazyLoadModulesConfig();
		String name = modulesConfig.getString(
				ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_IGNORE);
		if (name == null)
			return null;
		return IgnoreSubmoduleMode.valueOf(name.trim().toUpperCase());
	}

