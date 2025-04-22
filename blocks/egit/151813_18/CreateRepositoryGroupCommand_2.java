		RepositoryGroup group;
		try {
			group = groupsUtil.createGroup(newGroupName(groupsUtil));
		} catch (IllegalStateException e) {
			throw new ExecutionException(e.getLocalizedMessage(), e);
		}
		List<File> repoDirs = getSelectedRepositories(event);
		if (!repoDirs.isEmpty()) {
			groupsUtil.addRepositoriesToGroup(group, repoDirs);
