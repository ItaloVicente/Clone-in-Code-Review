	static String getNewGroupName(Shell shell, String title,
			RepositoryGroups groupsUtil, String initialName) {
		InputDialog inputDialog = new InputDialog(shell, title,
				UIText.RepositoriesView_RepoGroup_EnterName, initialName,
				name -> {
					if (name == null
							|| StringUtils.isEmptyOrNull(name.trim())) {
						return UIText.RepositoriesView_RepoGroup_EmptyNameError;
					}
					if (groupsUtil.groupExists(name.trim())) {
						return MessageFormat.format(
								UIText.RepositoryGroups_DuplicateGroupNameError,
								name.trim());
					}
					return null;
				});

		if (inputDialog.open() == Window.OK) {
			return inputDialog.getValue().trim();
		} else {
			return null;
