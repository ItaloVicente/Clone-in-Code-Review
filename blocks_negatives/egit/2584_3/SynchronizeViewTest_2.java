		String tagName = "exchangeCompareSidesInGitChangeSet";
		resetRepository(PROJ1);
		createTag(PROJ1, tagName);
		changeFilesInProject();
		commit(PROJ1);
		showDialog(PROJ1, "Team", "Synchronize...");
