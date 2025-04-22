		ContextMenuHelper.clickContextMenu(tree,
				UIText.RepositoriesView_DeleteBranchMenu);

		SWTBotShell deleteBranchDialog = bot
				.shell(UIText.RepositoriesView_ConfirmDeleteTitle);
		assertNotNull(deleteBranchDialog.bot().table(0).getTableItem(
				"refs/heads/abc"));
		assertNotNull(deleteBranchDialog.bot().table(0).getTableItem(
				"refs/heads/123"));
		deleteBranchDialog.bot().button(IDialogConstants.OK_LABEL).click();
