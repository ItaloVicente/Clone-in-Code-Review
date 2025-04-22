
		String initialContent = getTestFileContent();
		createTag("ResetToFirst", "The first tag");
		touchAndSubmit(null);
		String newContent = getTestFileContent();
		assertFalse("Wrong content", initialContent.equals(newContent));
		createTag("ResetToSecond", "The second tag");
		refreshAndWait();
		myRepoViewUtil.getTagsItem(tree, repositoryFile).expand().getNode(
				"ResetToFirst").select();

		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("ResetCommand"));

		SWTBotShell resetDialog = bot.shell(UIText.ResetCommand_WizardTitle);
		resetDialog.bot().radio(
				UIText.ResetTargetSelectionDialog_ResetTypeHardButton).click();
		waitInUI();
		resetDialog.bot().button(IDialogConstants.FINISH_LABEL).click();
		waitInUI();

		bot.shell(UIText.ResetTargetSelectionDialog_ResetQuestion).bot()
				.button(IDialogConstants.YES_LABEL).click();

		Job.getJobManager().join(JobFamilies.RESET, null);

		ResourcesPlugin.getWorkspace().getRoot().refreshLocal(
				IResource.DEPTH_INFINITE, null);
		assertEquals("Wrong content", initialContent, getTestFileContent());
