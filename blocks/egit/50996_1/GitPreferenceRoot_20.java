	private static void loadUserScopedConfig() {
		if (userScopedConfig == null || userScopedConfig.isOutdated()) {
			userScopedConfig = SystemReader.getInstance()
					.openUserConfig(null, FS.DETECTED);
			try {
				userScopedConfig.load();
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, true);
			} catch (ConfigInvalidException e) {
				Activator.handleError(e.getMessage(), e, true);
			}
			diffToolsList = getCustomDiffTools();
			mergeToolsList = getCustomMergeTools();
			if (userScopedConfigChangeListener != null) {
				userScopedConfigChangeListener = userScopedConfig
						.addChangeListener(new ConfigChangedListener() {
							@Override
							public void onConfigChanged(
									ConfigChangedEvent event) {
								diffToolsList = getCustomDiffTools();
								mergeToolsList = getCustomMergeTools();
							}
						});
			}
