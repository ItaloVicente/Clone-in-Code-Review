
		String initialContent = getTestFileContent();
		createTag("ResetToFirst", "The first tag");
		touchAndSubmit();
		String newContent = getTestFileContent();
		assertFalse("Wrong content", initialContent.equals(newContent));
		createTag("ResetToSecond", "The second tag");
		refreshAndWait();
		getTagsItem(tree, repositoryFile).expand().getNode("ResetToFirst")
				.select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("ResetCommand"));

		SWTBotShell resetDialog = bot.shell(UIText.ResetCommand_WizardTitle);
		pressAltAndChar(resetDialog, 'H');
		resetDialog.bot().button(IDialogConstants.FINISH_LABEL).click();
		waitInUI();

		bot.shell(UIText.ResetTargetSelectionDialog_ResetQuestion).bot()
				.button(IDialogConstants.YES_LABEL).click();
		assertEquals("Wrong content", initialContent, getTestFileContent());
