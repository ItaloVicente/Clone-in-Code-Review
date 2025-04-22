	private SWTBotTreeItem getTreeItemContainingText(SWTBotTreeItem[] items,
			String text) {
		for (SWTBotTreeItem item : items)
			if (item.getText().contains(text))
				return item;

		throw new WidgetNotFoundException(
					"Tree item elment containing text: test commit was not found");
	}

	private SWTBotEditor getCompareEditorForFileInWorkspaceModel()
