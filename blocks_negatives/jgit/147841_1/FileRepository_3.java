	private void loadSystemConfig() throws IOException {
		try {
			systemConfig.load();
		} catch (ConfigInvalidException e) {
			throw new IOException(MessageFormat.format(JGitText
					.get().systemConfigFileInvalid, systemConfig.getFile()
							.getAbsolutePath(),
					e), e);
		}
	}

	private void loadUserConfig() throws IOException {
		try {
			userConfig.load();
		} catch (ConfigInvalidException e) {
			throw new IOException(MessageFormat.format(JGitText
					.get().userConfigFileInvalid, userConfig.getFile()
							.getAbsolutePath(),
					e), e);
		}
	}

