	private static String newGroupName(RepositoryGroups groups) {
		for (int i = 0; i < 100; i++) {
			String name = MessageFormat.format(
					UIText.RepositoriesView_NewGroupFormat, Integer.valueOf(i));
			if (!groups.groupExists(name)) {
				return name;
			}
