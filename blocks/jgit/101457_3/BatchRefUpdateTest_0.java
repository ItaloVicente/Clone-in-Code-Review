		StoredConfig cfg = diskRepo.getConfig();
		cfg.load();
		cfg.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_LOGALLREFUPDATES
		cfg.save();

