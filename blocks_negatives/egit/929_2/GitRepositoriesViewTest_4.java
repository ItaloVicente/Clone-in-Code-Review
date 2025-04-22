	@Ignore
	public void testOpenFirstLevel() throws Exception {
		final SWTBotView view = getOrOpenView();
		final SWTBotTreeItem[] items = view.bot().tree().getAllItems();
		items[0].expand();
		SWTBotTreeItem[] children;

		children = items[0].getItems();
		assertEquals("Wrong number of children", 5, children.length);
