		bot.shell(UIText.DiscardChangesAction_confirmActionTitle).bot()
				.button(IDialogConstants.OK_LABEL).click();
		TestUtil.joinJobs(JobFamilies.DISCARD_CHANGES);
		ResourcesPlugin.getWorkspace().getRoot()
				.refreshLocal(IResource.DEPTH_INFINITE, null);
		waitInUI();
		String oldContent = getTestFileContent();
		assertFalse(newContent.equals(oldContent));
