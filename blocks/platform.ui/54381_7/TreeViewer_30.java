		preservingSelection(() -> {
			TreePath removedPath = null;
			if (internalIsInputOrEmptyPath(parentOrTreePath)) {
				Tree tree = (Tree) getControl();
				if (index < tree.getItemCount()) {
					TreeItem item1 = tree.getItem(index);
					if (item1.getData() != null) {
						removedPath = getTreePathFromItem(item1);
						disassociate(item1);
