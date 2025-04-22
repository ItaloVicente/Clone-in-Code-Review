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
			LOG.error(e.getMessage(), e);
