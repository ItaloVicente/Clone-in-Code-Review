				monitor
						.setTaskName(NLS
								.bind(
										UIText.RepositorySearchDialog_RepositoriesFound_message,
										Integer.valueOf(strings.size())));
				if (!lookForNestedRepositories)
					return;
			} else if (child.isDirectory()) {
