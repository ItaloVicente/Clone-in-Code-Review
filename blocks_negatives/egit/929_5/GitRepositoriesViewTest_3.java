	@Ignore
	public void testShowIn() throws Exception {
		SWTBotTree tree = bot.viewById("org.eclipse.jdt.ui.PackageExplorer")
				.bot().tree();
		tree.getAllItems()[0].select();
		ContextMenuHelper.clickContextMenu(tree, "Show In", viewName);

		SWTBotTree viewerTree = getOrOpenView().bot().tree();

		TableCollection selection = viewerTree.selection();
		assertTrue("Selection should contain one eelement", selection
				.rowCount() == 1);
		String nodeText = selection.get(0).get(0);
		assertTrue("Node text should contain project name", nodeText
				.contains(myProject.getName()));

		tree.select(tree.getAllItems()[0].expand().getNode("folder").expand()
				.getNode("test.txt"));

		ContextMenuHelper.clickContextMenu(tree, "Show In", viewName);

		selection = viewerTree.selection();
		assertTrue("Selection should contain one eelement", selection
				.rowCount() == 1);
		nodeText = selection.get(0).get(0);
		assertTrue("Node text should contain file name", nodeText
				.contains("test.txt"));
