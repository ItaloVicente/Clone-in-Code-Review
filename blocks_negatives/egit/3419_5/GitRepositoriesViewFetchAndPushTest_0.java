		myRepoViewUtil.getRemotesItem(tree, clonedRepositoryFile).expand().getNode(
				"origin").expand().getNode(0).select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("SimpleFetchCommand"));

		SWTBotShell confirm = bot.shell(dialogTitle);
		assertEquals("Wrong result table row count", 0, confirm.bot().table()
