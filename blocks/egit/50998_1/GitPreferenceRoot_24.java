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
		}
	}

	private static String[][] getCustomDiffOrMergeTools(
			String sectionName4BaseTool, String[][] baseToolAttributes,
			String sectionName4AllTools, String[][] allToolAttributes,
			BaseToolManager manager) {
		List<String> toolsList = loadToolManager(sectionName4BaseTool,
				baseToolAttributes, sectionName4AllTools, allToolAttributes,
				manager);
		String[][] toolsArray = new String[toolsList.size()][2];
		for (int index = 0; index < toolsList.size(); index++) {
			toolsArray[index][0] = toolsList.get(index);
			toolsArray[index][1] = toolsList.get(index);
		}
		return toolsArray;
	}

	private static List<String> loadToolManager(String sectionName4BaseTool,
			String[][] baseToolAttributes, String sectionName4AllTools,
			String[][] allToolAttributes, BaseToolManager manager) {
		List<String> toolsList = new ArrayList<String>();
		manager.removeAllUserDefinitions();
		if (userScopedConfig != null) {
			loadExternalToolAttributes(userScopedConfig, sectionName4BaseTool,
					null, manager, baseToolAttributes, true);
			loadExternalToolAttributes(userScopedConfig, sectionName4AllTools,
					null, manager, allToolAttributes, true);
			String defaultDiffMergeTool = manager.getDefaultToolName();
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
				for (String diffMergeToolName : diffMergeTools) {
					toolsList.add(diffMergeToolName);
					addExternalTool(userScopedConfig,
							sectionName4AllTools, diffMergeToolName, manager);
					loadExternalToolAttributes(userScopedConfig,
							sectionName4AllTools, diffMergeToolName, manager,
							allToolAttributes, false);
				}
			}
		}
		return toolsList;
	}

	private static String[][] getCustomMergeTools() {
		BaseToolManager manager = MergeToolManager.getInstance();
		String[][] baseToolAttributes = { { "tool", null } //$NON-NLS-1$
		};
		String[][] allToolAttributes = { { "prompt", "true" }, //$NON-NLS-1$ //$NON-NLS-2$
				{ "trustExitCode", "false" }, //$NON-NLS-1$ //$NON-NLS-2$
				{ "keepBackup", "true" }, //$NON-NLS-1$ //$NON-NLS-2$
				{ "keepTemporaries", "false" }, //$NON-NLS-1$ //$NON-NLS-2$
				{ "writeToTemp", "false" } //$NON-NLS-1$ //$NON-NLS-2$
		};
		System.out.println("----- getCustomMergeTools -----"); //$NON-NLS-1$
		return getCustomDiffOrMergeTools(ConfigConstants.CONFIG_KEY_MERGE,
				baseToolAttributes, "mergetool", //$NON-NLS-1$
				allToolAttributes, manager);
	}

	private static String[][] getCustomDiffTools() {
		BaseToolManager manager = DiffToolManager.getInstance();
		String[][] baseToolAttributes = { { "tool", null }, //$NON-NLS-1$
				{ "guitool", null } //$NON-NLS-1$
		};
		String[][] allToolAttributes = { { "prompt", "true" }, //$NON-NLS-1$ //$NON-NLS-2$
				{ "trustExitCode", "false" }, //$NON-NLS-1$ //$NON-NLS-2$
				{ "keepTemporaries", "false" }, //$NON-NLS-1$ //$NON-NLS-2$
				{ "writeToTemp", "false" } //$NON-NLS-1$ //$NON-NLS-2$
		};
		System.out.println("----- getCustomDiffTools -----"); //$NON-NLS-1$
		return getCustomDiffOrMergeTools(ConfigConstants.CONFIG_DIFF_SECTION,
				baseToolAttributes, "difftool", //$NON-NLS-1$
				allToolAttributes, manager);
	}

	private static void loadExternalToolAttributes(FileBasedConfig config,
			String sectionName,
			String subSectionName, BaseToolManager manager,
			String[][] attributes, boolean useDefault) {
		for (String[] attr : attributes) {
			String attrName = attr[0];
			String attrDefValue = attr[1];
			String attrValue = config.getString(sectionName, subSectionName, attrName);
			if (attrValue != null) {
				manager.addAttribute(subSectionName, attrName, attrValue);
				System.out
						.println("addAttribute: FOUND: " + subSectionName + ", " //$NON-NLS-1$ //$NON-NLS-2$
						+ attrName + ", " + attrValue); //$NON-NLS-1$
			} else if (useDefault && attrDefValue != null) {
				manager.addAttribute(subSectionName, attrName, attrDefValue);
				System.out.println(
						"addAttribute: DEFAULT: " + subSectionName + ", " //$NON-NLS-1$ //$NON-NLS-2$
								+ attrName + ", " + attrDefValue); //$NON-NLS-1$
			}
		}
	}

	private static void addExternalTool(FileBasedConfig config,
			String sectionName,
			String toolName, BaseToolManager manager) {
		if (userScopedConfig != null) {
			String toolPath = config.getString(sectionName, toolName,
					"path"); //$NON-NLS-1$
			if (toolPath != null && !toolPath.equals("")) { //$NON-NLS-1$
				manager.addUserOverloadedTool(toolName, toolPath);
			} else {
				String toolCmd = config.getString(sectionName,
						toolName, "cmd"); //$NON-NLS-1$
				if (toolCmd != null && !toolCmd.equals("")) { //$NON-NLS-1$
					manager.addUserDefinedTool(toolName, toolCmd);
				}
			}
		}
	}

