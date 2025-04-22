					item1.dispose();
				}
			} else {
				Widget[] parentItems = internalFindItems(parentOrTreePath);
				for (Widget parentWidget : parentItems) {
					TreeItem parentItem = (TreeItem) parentWidget;
					if (parentItem.isDisposed())
						continue;
					if (index < parentItem.getItemCount()) {
						TreeItem item2 = parentItem.getItem(index);

						if (item2.getData() == null) {
							if (index > 0 || parentItem.getExpanded()) {
								item2.dispose();
