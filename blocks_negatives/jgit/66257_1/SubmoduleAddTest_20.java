		modulesConfig.load();
		assertEquals(path1, modulesConfig.getString(
				ConfigConstants.CONFIG_SUBMODULE_SECTION, path1,
				ConfigConstants.CONFIG_KEY_PATH));
		assertEquals(url1, modulesConfig.getString(
				ConfigConstants.CONFIG_SUBMODULE_SECTION, path1,
				ConfigConstants.CONFIG_KEY_URL));
		assertEquals(path2, modulesConfig.getString(
				ConfigConstants.CONFIG_SUBMODULE_SECTION, path2,
				ConfigConstants.CONFIG_KEY_PATH));
		assertEquals(url2, modulesConfig.getString(
				ConfigConstants.CONFIG_SUBMODULE_SECTION, path2,
				ConfigConstants.CONFIG_KEY_URL));
