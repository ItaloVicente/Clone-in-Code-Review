	@Test
	public void testWithStagingView() throws Exception {
		SWTBotTree tree = getOrOpenView().bot().tree();
		StagingViewTester stagingViewTester = StagingViewTester
				.openStagingView();
		TestUtil.waitForDecorations();
		SWTBotTreeItem item = myRepoViewUtil.getRootItem(tree, repositoryFile);
		item.select();
		TestUtil.waitForDecorations();
		stagingViewTester.getView().close();
		TestUtil.waitForDecorations();
		SWTBotTreeItem[] items = tree.getAllItems();
		boolean[] hasNull = { false };
		String[] unknownClass = { "" };
		tree.widget.getDisplay().syncExec(() -> {
			for (SWTBotTreeItem i : items) {
				Object obj = i.widget.getData();
				if (!(obj instanceof RepositoryTreeNode)) {
					unknownClass[0] = obj.getClass().getName();
					break;
				}
				if (((RepositoryTreeNode) obj).getRepository() == null) {
					hasNull[0] = true;
					break;
				}
			}
		});
		assertEquals("Unknown tree element", "", unknownClass[0]);
		assertFalse("Tree has node with null repository", hasNull[0]);
	}

