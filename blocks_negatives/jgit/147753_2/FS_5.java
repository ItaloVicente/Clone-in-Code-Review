			StoredConfig userConfig = SystemReader.getInstance()
					.openUserConfig(null, FS.DETECTED);
			try {
				userConfig.load();
			} catch (IOException e) {
				LOG.error(MessageFormat.format(JGitText.get().readConfigFailed,
						userConfig), e);
			} catch (ConfigInvalidException e) {
				LOG.error(MessageFormat.format(
						JGitText.get().repositoryConfigFileInvalid,
						userConfig,
						e.getMessage()));
			}
