		String groupName = getNewGroupName(getActiveShell(event),
				UIText.RepositoriesView_RepoGroup_Create_Title, groupsUtil, ""); //$NON-NLS-1$
		if (groupName != null) {
			UUID groupId = groupsUtil.createGroup(groupName);
			List<File> repoDirs = getSelectedRepositories(event);
			if (!repoDirs.isEmpty()) {
				groupsUtil.addRepositoriesToGroup(groupId, repoDirs);
			}
			getView(event).refresh();
