	private SWTBotShell waitForFetchResultDialog() {
		String destinationString = clonedRepositoryFile.getParentFile()
				.getName() + " - " + "origin";
		String dialogTitle = NLS.bind(UIText.FetchResultDialog_title,
				destinationString);

		Matcher<Shell> withText = withText(dialogTitle);
		bot.waitUntil(waitForShell(withText), SWTBotPreferences.TIMEOUT * 2);

		SWTBotShell confirm = bot.shell(dialogTitle);
		return confirm;
