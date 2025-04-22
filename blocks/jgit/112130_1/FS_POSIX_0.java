	@SuppressWarnings("boxing")
	private void determineAtomicFileCreationSupport() {
		Boolean ret = getAtomicFileCreationSupportOption(
				SystemReader.getInstance().openUserConfig(null
		if (ret == null && StringUtils.isEmptyOrNull(SystemReader.getInstance()
				.getenv(Constants.GIT_CONFIG_NOSYSTEM_KEY))) {
			ret = getAtomicFileCreationSupportOption(
					SystemReader.getInstance().openSystemConfig(null
		}
		supportsAtomicCreateNewFile = (ret == null) || ret;
	}

	private Boolean getAtomicFileCreationSupportOption(FileBasedConfig config) {
		try {
			config.load();
			String value = config.getString(ConfigConstants.CONFIG_CORE_SECTION
					null
					ConfigConstants.CONFIG_KEY_SUPPORTSATOMICFILECREATION);
			if (value == null) {
				return null;
			}
			return Boolean.valueOf(StringUtils.toBoolean(value));
		} catch (IOException | ConfigInvalidException e) {
			return Boolean.TRUE;
		}
	}

