	private void makeChangesAndCommit(String projectName) throws Exception {
		changeFilesInProject();

		showDialog(projectName, "Team", UIText.CommitAction_commit);

		bot.shell(UIText.CommitDialog_CommitChanges).bot().activeShell();
		bot.styledText(0).setText("test commit");
		bot.button(UIText.CommitDialog_Commit).click();
	}

	private void showDialog(String projectName, String... cmd) {
