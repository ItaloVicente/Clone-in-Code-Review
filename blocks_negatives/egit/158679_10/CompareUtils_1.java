					diffToolMgr.compare(local, remote, toolName, prompt, gui,
							trustExitCode, promptContinueHandler, tools -> {
								ToolsUtils.informUser(
										UIText.CompareUtils_NoDiffToolsDefined,
										UIText.CompareUtils_NoDiffToolSpecified);
							});
