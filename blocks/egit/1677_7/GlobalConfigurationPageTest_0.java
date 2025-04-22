	@AfterClass
	public static void afterTest() throws Exception {
		SWTBotShell preferencePage = new Eclipse().openPreferencePage(null);
		preferencePage.bot().tree(0).getTreeItem("General").select();
		preferencePage.bot().button(IDialogConstants.OK_LABEL).click();
	}

