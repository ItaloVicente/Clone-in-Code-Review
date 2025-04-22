	private StoredConfig getUserConfig() throws IOException {
		StoredConfig userConfig = null;
		try {
			userConfig = SystemReader.getInstance().getUserConfig();
		} catch (ConfigInvalidException e) {
			throw die(e.getMessage());
		}
		return userConfig;
	}

