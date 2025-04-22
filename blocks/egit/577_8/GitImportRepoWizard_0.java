	public int configuredRepoCount() {
		bot.shell("Import Projects from Git").activate();

		return bot.table(0).rowCount();
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
	}

