	private void loadPathNames() {
		pathToName = null;
		if (modulesConfig != null) {
			HashMap<String
			for (String name : modulesConfig
					.getSubsections(ConfigConstants.CONFIG_SUBMODULE_SECTION)) {
				pathNames.put(modulesConfig.getString(
						ConfigConstants.CONFIG_SUBMODULE_SECTION
						ConfigConstants.CONFIG_KEY_PATH)
			}
			pathToName = pathNames;
		}
	}

