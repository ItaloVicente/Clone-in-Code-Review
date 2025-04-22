	private void resetRepository(String projectName) {
		showDialog(projectName, "Team", "Reset...");

		bot.shell(UIText.ResetCommand_WizardTitle).bot().activeShell();
		bot.radio(UIText.ResetTargetSelectionDialog_ResetTypeHardButton)
				.click();
		bot.button(UIText.ResetTargetSelectionDialog_ResetButton).click();

		bot.shell(UIText.ResetTargetSelectionDialog_ResetQuestion).bot()
				.activeShell();
		bot.button("Yes").click();

	}

	private void createTag(String projectName, String tagName) {
		showDialog(projectName, "Team", "Tag...");

		bot.shell("Create new tag").bot().activeShell();
		bot.text(0).setFocus();
		bot.text(0).setText(tagName);
		bot.text(1).setFocus();
		bot.text(1).setText(tagName);
		bot.button("OK").click();
	}

	private void makeChangesAndCommit(String projectName) throws Exception {
		changeFilesInProject();

		showDialog(projectName, "Team", UIText.CommitAction_commit);

		bot.shell(UIText.CommitDialog_CommitChanges).bot().activeShell();
		bot.styledText(0).setText("test commit");
		bot.button(UIText.CommitDialog_Commit).click();
	}

	private void showDialog(String projectName, String... cmd) {
