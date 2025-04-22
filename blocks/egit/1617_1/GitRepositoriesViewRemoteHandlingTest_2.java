		remotesItem.getNode("test").select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("OpenPropertiesCommand"));
		waitInUI();
		assertEquals("org.eclipse.ui.views.PropertySheet", bot.activeView()
				.getReference().getId());

