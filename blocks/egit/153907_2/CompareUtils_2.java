					if (DiffPreferencePage
							.getDiffToolMode() == DiffToolMode.GIT_CONFIG) {
						if (!toolName.isPresent()) {
							toolName = DiffPreferencePage
									.getDiffToolFromGitCongig();
						}
					}
