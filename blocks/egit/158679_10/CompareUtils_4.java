					if (preferencesCommand != null) {
						String customToolName = "custom_tool_" //$NON-NLS-1$
								+ DiffMergeSettings
								.getFileExtension(changedFilePath);
						UserDefinedDiffTool tool = new UserDefinedDiffTool(
								customToolName, "", preferencesCommand); //$NON-NLS-1$
						diffToolMgr.compare(local, remote, tool, true);
					} else {
						diffToolMgr.compare(local, remote, toolName, prompt,
								gui, trustExitCode, promptContinueHandler,
								tools -> {
									ToolsUtils.informUser(
											UIText.CompareUtils_NoDiffToolsDefined,
											UIText.CompareUtils_NoDiffToolSpecified);
								});
					}
