			StoredConfig userConfig;
			try {
				userConfig = SystemReader.getInstance().getUserConfig();
			} catch (IOException | ConfigInvalidException e) {
				LOG.error(JGitText.get().saveFileStoreAttributesFailed
				return;
			}
