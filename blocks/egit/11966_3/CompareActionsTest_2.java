	private void closeFirstEmptySynchronizeDialog() {
		SWTBotShell[] shells = bot.shells();
		for (int i = 0; i < shells.length; i++) {
			SWTBotShell shell = shells[i];
			if ("Synchronize Complete - Git".equals(shell.getText()))
				shell.close();
		}
	}

	private void assertSynchronizeNoChange() {
		SWTBotLabel syncViewLabel = bot.viewByTitle("Synchronize").bot()
				.label(2);

		String noResultLabel = syncViewLabel.getText();
		assertTrue(noResultLabel.contains("No changes")
				|| noResultLabel.contains("No differences"));
	}

	private void assertSynchronizeFile1Changed() {
		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		SWTBotTreeItem[] syncItems = syncViewTree.getAllItems();
		assertEquals(syncItems.length, 1);
		assertTrue(syncItems[0].getText().contains(PROJ1));

		syncItems[0].expand();
		SWTBotTreeItem[] level1Children = syncItems[0].getItems();
		assertEquals(level1Children.length, 1);
		assertTrue(level1Children[0].getText().contains(FOLDER));

		level1Children[0].expand();
		SWTBotTreeItem[] level2Children = level1Children[0].getItems();
		assertEquals(level2Children.length, 1);
		assertTrue(level2Children[0].getText().contains(FILE1));
