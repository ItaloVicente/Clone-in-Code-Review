	private String getPruneExpireStr() {
		return repo.getConfig().getString(
                        ConfigConstants.CONFIG_GC_SECTION
                        ConfigConstants.CONFIG_KEY_PRUNEEXPIRE);
	}

