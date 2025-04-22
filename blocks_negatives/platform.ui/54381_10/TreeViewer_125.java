		preservingSelection(new Runnable() {
			@Override
			public void run() {
				TreePath removedPath = null;
				if (internalIsInputOrEmptyPath(parentOrTreePath)) {
					Tree tree = (Tree) getControl();
					if (index < tree.getItemCount()) {
						TreeItem item = tree.getItem(index);
						if (item.getData() != null) {
							removedPath = getTreePathFromItem(item);
							disassociate(item);
						}
						item.dispose();
