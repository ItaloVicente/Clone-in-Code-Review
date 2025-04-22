		StoredConfig userConfig = null;
		try {
			userConfig = SystemReader.getInstance().getUserConfig();
		} catch (ConfigInvalidException e) {
			LOG.error(MessageFormat.format(JGitText.get().userConfigInvalid
					userConfig
			throw new IOException(e.getMessage()
		}
