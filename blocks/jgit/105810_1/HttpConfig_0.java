		init(config
	}

	public HttpConfig(URIish uri) {
		FileBasedConfig userConfig = SystemReader.getInstance()
				.openUserConfig(null
		try {
			userConfig.load();
		} catch (IOException | ConfigInvalidException e) {
			LOG.error(MessageFormat.format(JGitText.get().userConfigFileInvalid
					userConfig.getFile().getAbsolutePath()
			init(new Config()
			return;
		}
		init(userConfig
	}

	private void init(Config config
