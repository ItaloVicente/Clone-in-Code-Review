		final SWTBotTreeItem[] items = tree.getAllItems();
		boolean found = false;
		for (SWTBotTreeItem item : items) {
			if (item.getText().startsWith(
					repositoryFile.getParentFile().getName() + "Cloned")) {
				found = true;
				break;
			}
		}
		assertTrue("Tree should have item with correct text", found);
