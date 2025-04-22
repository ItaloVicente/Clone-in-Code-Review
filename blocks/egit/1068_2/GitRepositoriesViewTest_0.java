		shell.bot().button(UIText.WizardProjectsImportPage_deselectAll).click();
		assertTrue(!shell.bot().button(IDialogConstants.FINISH_LABEL)
				.isEnabled());
		shell.bot().button(UIText.WizardProjectsImportPage_selectAll).click();
		assertTrue(shell.bot().button(IDialogConstants.FINISH_LABEL)
				.isEnabled());
		shell.bot().button(IDialogConstants.FINISH_LABEL).click();
