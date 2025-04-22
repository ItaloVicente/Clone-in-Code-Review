		mode = repo.getConfig().getEnum(FetchRecurseSubmodulesMode.values()
				ConfigConstants.CONFIG_FETCH_SECTION
				ConfigConstants.CONFIG_KEY_RECURSE_SUBMODULES
		if (mode != null) {
			return mode;
		}

