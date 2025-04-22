	protected void assertClickOpens(SWTBotTree tree, String menu, String window)
			throws InterruptedException {
		ContextMenuHelper.clickContextMenu(tree, menu);
		SWTBotShell shell = bot.shell(window);
		shell.activate();
		waitInUI();
		shell.bot().button(IDialogConstants.CANCEL_LABEL).click();
		shell.close();
	}

