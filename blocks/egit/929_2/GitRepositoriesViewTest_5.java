	@Test
	public void testExpandFirstLevel() throws Exception {
		SWTBotTree tree = getOrOpenView().bot().tree();
		SWTBotTreeItem item = getRootItem(tree, repositoryFile).expand();
		SWTBotTreeItem[] children = item.getItems();
		assertEquals("Wrong number of children", 5, children.length);
