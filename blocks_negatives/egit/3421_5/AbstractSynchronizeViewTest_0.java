	protected void launchSynchronization(String repo, String projectName,
			String srcRepo, String srcRef, String dstRepo, String dstRef,
			boolean includeLocal) throws InterruptedException {
		showDialog(projectName, "Team", "Synchronize...");

		bot.shell("Synchronize repository: " + repo + File.separator + ".git");

		if (!includeLocal)
			bot.checkBox(
					UIText.SelectSynchronizeResourceDialog_includeUncommitedChanges)
					.click();
