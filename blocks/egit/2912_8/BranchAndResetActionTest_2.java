		SWTBotShell dialog = openRenameBranchDialog();

		dialog.bot().tree().getTreeItem(LOCAL_BRANCHES).expand()
				.getNode("stable").select();
		dialog.bot().button(UIText.RenameBranchDialog_RenameButtonLabel)
				.click();
		SWTBotShell newNameDialog = bot
				.shell(UIText.RenameBranchDialog_RenameBranchDialogNewNameInputWindowTitle);
		newNameDialog.bot().text().setText("master");
		assertFalse(newNameDialog.bot().button(IDialogConstants.OK_LABEL)
