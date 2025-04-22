		RepositoryGroups groupsUtil = RepositoryGroups.getInstance();

		RepositoryGroup group = getSelectedGroup(event);
		String groupName =
				CreateRepositoryGroupCommand.getNewGroupName(getActiveShell(event),
						UIText.RepositoriesView_RepoGroup_Rename_Title,
						groupsUtil,
						group.getName());
		if (groupName != null) {
			groupsUtil.renameGroup(group, groupName);
			getView(event).refresh();
		}
		return null;
	}

	private RepositoryGroup getSelectedGroup(ExecutionEvent event)
			throws ExecutionException {
