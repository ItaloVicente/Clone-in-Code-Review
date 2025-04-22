				try {
					DiffToolManager diffToolMgr = new DiffToolManager(
							repository);

					Optional<String> toolName = Optional.empty();
					if (DiffPreferencePage
							.getDiffToolMode() == DiffToolMode.EXTERNAL_FOR_TYPE
							|| DiffPreferencePage
									.getDiffToolMode() == DiffToolMode.GIT_CONFIG) {
						try {
							toolName = diffToolMgr
									.getExternalToolFromAttributes(repository,
											mergedFilePath);
						} catch (ToolException e) {
							Activator.handleError(
									UIText.CompareUtils_GitConfigurationErrorText,
									e, true);
						}
					}
