	private boolean config_filemode() {
		StoredConfig config = repository.getConfig();
		Boolean filemode = Boolean.valueOf(config.getBoolean("core", null, //$NON-NLS-1$
				"filemode", true)); //$NON-NLS-1$
		return filemode.booleanValue();
	}

