	private void assertTreeCompareNoChange() {
		SWTBotTree tree = waitUntilCompareTreeViewTreeHasNodeCount(1);
		SWTBotTreeItem[] items = tree.getAllItems();
		assertEquals(1, items.length);
		assertEquals(UIText.CompareTreeView_NoDifferencesFoundMessage,
				items[0].getText());
	}

	private void assertTreeCompareChanges(int nodeCount) {
		SWTBotTree tree = waitUntilCompareTreeViewTreeHasNodeCount(nodeCount);
		SWTBotTreeItem[] items = tree.getAllItems();
		assertThat(items[0].getText(),
				not(UIText.CompareTreeView_NoDifferencesFoundMessage));
	}
