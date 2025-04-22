	public static SWTBotTreeItem navigateTo(SWTBotTree tree, String... path) {
		assertNotNull(path);

		new SWTBot().waitUntil(widgetIsEnabled(tree));
		SWTBotTreeItem item = getNode(tree.getAllItems(), path[0]);
		for (int i = 1; item != null && i < path.length; i++) {
			item.expand();
			item = getChildNode(item, path[i]);
		}
		return item;
	}

