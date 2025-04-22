			StoredConfig userConfig;
			try {
				userConfig = GlobalConfigCache.getInstance().getUserConfig();
			} catch (IOException | ConfigInvalidException e) {
				LOG.error(JGitText.get().saveFileStoreAttributesFailed
				return;
			}
