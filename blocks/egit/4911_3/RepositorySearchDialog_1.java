				monitor.setTaskName(NLS
						.bind(UIText.RepositorySearchDialog_RepositoriesFound_message,
								Integer.valueOf(strings.size())));
			} else if (FileKey.isGitRepository(new File(child,
					Constants.DOT_GIT), FS.DETECTED)) {
				try {
					strings.add(new File(child, Constants.DOT_GIT)
							.getCanonicalPath());
				} catch (IOException e) {
				}
				monitor.setTaskName(NLS
						.bind(UIText.RepositorySearchDialog_RepositoriesFound_message,
								Integer.valueOf(strings.size())));
			} else if (lookForNestedRepositories) {
