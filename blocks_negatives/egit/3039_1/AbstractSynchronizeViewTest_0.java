		showDialog(projectName, "Team", "Tag...");

		bot.shell("Create new tag").bot().activeShell();
		bot.text(0).setFocus();
		bot.text(0).setText(tagName);
		bot.styledText(0).setFocus();
		bot.styledText(0).setText(tagName);
		bot.button(IDialogConstants.OK_LABEL).click();
		TestUtil.joinJobs(JobFamilies.TAG);
