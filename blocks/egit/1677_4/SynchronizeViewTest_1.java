		resetRepository(PROJ1);
		createTag(PROJ1, "v0.1");
		makeChangesAndCommit(PROJ1);
		createTag(PROJ1, "v0.2");
		makeChangesAndCommit(PROJ1);
		showDialog(PROJ1, "Team", "Synchronize...");

		bot.shell("Synchronize repository: " + REPO1 + File.separator + ".git")
