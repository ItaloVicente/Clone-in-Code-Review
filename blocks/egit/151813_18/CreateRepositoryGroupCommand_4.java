	private static @NonNull String newGroupName(RepositoryGroups groups) {
		for (int i = 1; i < 100; i++) {
			String name = MessageFormat.format(
					UIText.RepositoriesView_NewGroupFormat, Integer.valueOf(i));
			if (name != null && !groups.groupExists(name)) {
				return name;
			}
