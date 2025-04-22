	@Ignore
	public void testShowProperties() throws Exception {
		SWTBotTree tree = getOrOpenView().bot().tree();
		SWTBotTreeItem item = getRootItem(tree, repositoryFile);
		item.select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("OpenPropertiesCommand"));
		waitInUI();
		assertEquals("org.eclipse.ui.views.PropertySheet", bot.activeView()
				.getReference().getId());
