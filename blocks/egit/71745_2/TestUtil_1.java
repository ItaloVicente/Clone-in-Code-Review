	public static SWTBotTreeItem expandAndWait(final SWTBotTreeItem treeItem) {
		treeItem.expand();
		new SWTBot().waitUntil(new DefaultCondition() {

			@Override
			public boolean test() {
				SWTBotTreeItem[] children = treeItem.getItems();
				return children != null && children.length > 0;
			}

			@Override
			public String getFailureMessage() {
				return "No children found for " + treeItem.getText();
			}
		});
		return treeItem;
	}

