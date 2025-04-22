		TestUtil.joinJobs(JobFamilies.CHECKOUT);
		dialog = openRenameBranchDialog();
		dialog.bot().tree().getTreeItem(LOCAL_BRANCHES).expand()
				.getNode("renamed");
		dialog.close();

		dialog = openRenameBranchDialog();
		dialog.bot().tree().getTreeItem(LOCAL_BRANCHES).expand()
				.getNode("renamed").select();
		dialog.bot().button(UIText.RenameBranchDialog_RenameButtonLabel)
				.click();
		newNameDialog = bot
				.shell(UIText.RenameBranchDialog_RenameBranchDialogNewNameInputWindowTitle);
