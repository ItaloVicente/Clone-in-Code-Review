		util.getProjectItems(projectExplorerTree, UNSHARED)[0].select();
		assertFalse("Add To Index should not be present",
				ContextMenuHelper.contextMenuItemExists(projectExplorerTree,
						"Team", addToIndexLabel));
		assertFalse("Remove From Index should not be present",
				ContextMenuHelper.contextMenuItemExists(projectExplorerTree,
						"Team", removeFromIndexLabel));
		projectExplorerTree.select(projectExplorerTree.getAllItems());
		assertFalse("Add To Index should not be present",
				ContextMenuHelper.contextMenuItemExists(projectExplorerTree,
						"Team", addToIndexLabel));
		assertFalse("Remove From Index should not be present",
				ContextMenuHelper.contextMenuItemExists(projectExplorerTree,
						"Team", removeFromIndexLabel));
		unshared.delete(true, null);
