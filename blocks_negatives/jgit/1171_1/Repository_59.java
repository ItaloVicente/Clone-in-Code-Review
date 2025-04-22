	private void loadUserConfig() throws IOException {
		try {
			userConfig.load();
		} catch (ConfigInvalidException e1) {
			IOException e2 = new IOException(MessageFormat.format(JGitText
					.get().userConfigFileInvalid, userConfig.getFile()
					.getAbsolutePath(), e1));
			e2.initCause(e1);
			throw e2;
		}
	}

	private void loadConfig() throws IOException {
		try {
			config.load();
		} catch (ConfigInvalidException e1) {
			IOException e2 = new IOException(JGitText.get().unknownRepositoryFormat);
			e2.initCause(e1);
			throw e2;
		}
	}


