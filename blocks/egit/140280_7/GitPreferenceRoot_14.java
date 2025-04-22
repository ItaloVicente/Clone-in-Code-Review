	private static void loadUserScopedConfig() {
		if (userScopedConfig == null || userScopedConfig.isOutdated()) {
			userScopedConfig = SystemReader.getInstance().openUserConfig(null,
					FS.DETECTED);
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
		}
	}

	private static String[][] getCustomMergeTools() {
		MergeToolManager mergeToolManager = new MergeToolManager(FS.DETECTED,
				null, null, userScopedConfig);
		return setToComboArray(mergeToolManager.getAllToolNames());
	}

	private static String[][] getCustomDiffTools() {
		DiffToolManager diffToolManager = new DiffToolManager(FS.DETECTED, null,
				null, userScopedConfig);
		return setToComboArray(diffToolManager.getAllToolNames());
	}

	private static String[][] setToComboArray(Set<String> toolsList) {
		String[][] toolsArray = new String[toolsList.size()][2];
		int index = 0;
		for (String toolName : toolsList) {
			toolsArray[index][0] = toolName;
			toolsArray[index][1] = toolName;
			index++;
		}
		return toolsArray;
	}

