	private FetchRecurseSubmodulesMode getRecurseMode(String p
		return config.getEnum(FetchRecurseSubmodulesMode.values()
				ConfigConstants.CONFIG_SUBMODULE_SECTION
				p
				ConfigConstants.CONFIG_KEY_FETCH_RECURSE_SUBMODULES
				null);
	}

