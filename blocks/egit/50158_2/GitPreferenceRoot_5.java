	private static StoredConfig loadUserScopedConfig() {
		StoredConfig c = SystemReader.getInstance().openUserConfig(null,
				FS.DETECTED);
		try {
			c.load();
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, true);
		} catch (ConfigInvalidException e) {
			Activator.handleError(e.getMessage(), e, true);
		}
		return c;
	}

	private String[][] getCustomDiffOrMergeTools(String sectionName4AllTools,
			String sectionName4DefaultTool) {
		List<String> toolsList = new ArrayList<String>();
		StoredConfig userScopedConfig = loadUserScopedConfig();
		if (userScopedConfig != null) {
			String defaultDiffMergeTool = userScopedConfig
					.getString(sectionName4DefaultTool, null, "tool"); //$NON-NLS-1$
			Set<String> diffMergeTools = userScopedConfig
					.getSubsections(sectionName4AllTools);
			if (diffMergeTools.size() == 0) {
				if (defaultDiffMergeTool != null) {
					toolsList.add(defaultDiffMergeTool);
				} else {
					toolsList.add("none"); //$NON-NLS-1$
				}
			} else {
				if (defaultDiffMergeTool != null
						&& !diffMergeTools.contains(defaultDiffMergeTool)) {
					toolsList.add(defaultDiffMergeTool);
				}
				for (String mergeTool : diffMergeTools) {
					toolsList.add(mergeTool);
				}
			}
		}
		String[][] toolsArray = new String[toolsList.size()][2];
		for (int index = 0; index < toolsList.size(); index++) {
			toolsArray[index][0] = toolsList.get(index);
			toolsArray[index][1] = toolsList.get(index);
		}
		return toolsArray;
	}

	private String[][] getCustomMergeTools() {
		return getCustomDiffOrMergeTools("mergetool", //$NON-NLS-1$
				ConfigConstants.CONFIG_KEY_MERGE);
	}

	private String[][] getCustomDiffTools() {
		return getCustomDiffOrMergeTools("difftool", //$NON-NLS-1$
				ConfigConstants.CONFIG_DIFF_SECTION);
	}

