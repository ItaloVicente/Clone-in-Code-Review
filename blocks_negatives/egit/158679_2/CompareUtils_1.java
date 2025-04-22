					diffToolMgr.compare(local, remote, toolName, unset, gui,
							unset, promptContinueHandler, tools -> {
								ToolsUtils.informUser(
										UIText.CompareUtils_NoDiffToolsDefined,
										UIText.CompareUtils_NoDiffToolSpecified);
							});
