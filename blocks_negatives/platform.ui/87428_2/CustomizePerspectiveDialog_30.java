		treeManager.addListener(new CheckListener() {
			@Override
			public void checkChanged(TreeItem changedItem) {
				if (changedItem instanceof Category) {
					menuCategoriesViewer.update(changedItem, null);
				} else if (changedItem instanceof ShortcutItem) {
					ShortcutItem item = (ShortcutItem) changedItem;
					if (item.getCategory() != null) {
						item.getCategory().update();
						updateCategoryAndParents(menuCategoriesViewer, item
								.getCategory());
					}
