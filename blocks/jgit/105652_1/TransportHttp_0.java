	private void updateSslVerifyUser(boolean value) {
		FileBasedConfig userConfig = SystemReader.getInstance()
				.openUserConfig(null
		try {
			userConfig.load();
			updateSslVerify(userConfig
		} catch (IOException | ConfigInvalidException e) {
			LOG.error(MessageFormat.format(JGitText.get().userConfigFileInvalid
					userConfig.getFile().getAbsolutePath()
		}
	}

