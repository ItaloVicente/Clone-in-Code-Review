	public int configuredRepoCount() {
		bot.shell("Import Projects from Git").activate();

		return bot.table(0).rowCount();
	}

	public boolean containsRepo(String projectName) {
		SWTBotTable table = bot.table(0);
		int repoCount = configuredRepoCount();

		for (int i = 0; i < repoCount; i++) {
			String rowName = table.getTableItem(i).getText();
			if (rowName.contains(projectName)) {
				return true;
			}
		}
		return false;
	}

	public void selectAndCloneRepository(int index) {
		bot.shell("Import Projects from Git").activate();

		bot.table(0).select(index);

		bot.button("Next >").click();

		bot.button("Next >").click();

		bot.button("Select All").click();
	}

	public void waitForCreate() {
		bot.button("Finish").click();

		SWTBotShell shell = bot.shell("Import Projects from Git");

		bot.waitUntil(shellCloses(shell), 120000);
	}

