	public void testHasRepo() throws Exception {
		final SWTBotView view = getOrOpenView();
		final SWTBotTreeItem[] items = view.bot().tree().getAllItems();
		boolean found = false;
		for (SWTBotTreeItem item : items) {
			if (item.getText().startsWith(PRJ_NAME)) {
				found = true;
				break;
			}
		}
		assertTrue("Tree should have item with correct text", found);
