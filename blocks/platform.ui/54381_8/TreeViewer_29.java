		preservingSelection(() -> {
			if (internalIsInputOrEmptyPath(elementOrTreePath)) {
				getTree().setItemCount(count);
				return;
			}
			Widget[] items = internalFindItems(elementOrTreePath);
			for (Widget item : items) {
				TreeItem treeItem = (TreeItem) item;
				treeItem.setItemCount(count);
