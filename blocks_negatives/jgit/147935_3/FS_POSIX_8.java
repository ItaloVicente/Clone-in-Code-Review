	private void determineAtomicFileCreationSupport() {
		AtomicFileCreation ret = getAtomicFileCreationSupportOption(
				SystemReader.getInstance().openUserConfig(null, this));
		if (ret == AtomicFileCreation.UNDEFINED
				&& StringUtils.isEmptyOrNull(SystemReader.getInstance()
						.getenv(Constants.GIT_CONFIG_NOSYSTEM_KEY))) {
			ret = getAtomicFileCreationSupportOption(
					SystemReader.getInstance().openSystemConfig(null, this));
		}
		supportsAtomicCreateNewFile = ret;
	}

	private AtomicFileCreation getAtomicFileCreationSupportOption(
			FileBasedConfig config) {
		try {
			config.load();
			String value = config.getString(ConfigConstants.CONFIG_CORE_SECTION,
					null,
					ConfigConstants.CONFIG_KEY_SUPPORTSATOMICFILECREATION);
			if (value == null) {
				return AtomicFileCreation.UNDEFINED;
			}
			return StringUtils.toBoolean(value)
					? AtomicFileCreation.SUPPORTED
					: AtomicFileCreation.NOT_SUPPORTED;
		} catch (IOException | ConfigInvalidException e) {
			return AtomicFileCreation.SUPPORTED;
		}
	}

