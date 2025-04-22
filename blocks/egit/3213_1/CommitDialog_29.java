		for (Repository repo : repos)
			createChangeIdDefault = createChangeIdDefault
					|| repo.getConfig().getBoolean(
							ConfigConstants.CONFIG_GERRIT_SECTION,
							ConfigConstants.CONFIG_KEY_CREATECHANGEID, false);

