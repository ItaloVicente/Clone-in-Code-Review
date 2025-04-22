			if (FileKey.isGitRepository(child, FS.DETECTED)) {
				strings.add(child.getAbsoluteFile());
				monitor.setTaskName(NLS
						.bind(UIText.RepositorySearchDialog_RepositoriesFound_message,
								Integer.valueOf(strings.size())));
			} else if (FileKey.isGitRepository(new File(child,
					Constants.DOT_GIT), FS.DETECTED)) {
				strings.add(
						new File(child, Constants.DOT_GIT).getAbsoluteFile());
				monitor.setTaskName(NLS
						.bind(UIText.RepositorySearchDialog_RepositoriesFound_message,
								Integer.valueOf(strings.size())));
			} else if (lookForNestedRepositories) {
