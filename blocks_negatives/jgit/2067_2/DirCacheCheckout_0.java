	private Boolean filemode;

	private boolean config_filemode() {
		if (filemode == null) {
			StoredConfig config = repo.getConfig();
			filemode = Boolean.valueOf(config.getBoolean("core", null,
					"filemode", true));
		}
		return filemode.booleanValue();
	}

