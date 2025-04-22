				} else {
					Widget[] parentItems = internalFindItems(parentOrTreePath);
					for (Widget parentWidget : parentItems) {
						TreeItem parentItem = (TreeItem) parentWidget;
						if (parentItem.isDisposed())
							continue;
						if (index < parentItem.getItemCount()) {
							TreeItem item = parentItem.getItem(index);

							if (item.getData() == null) {
								if (index > 0 || parentItem.getExpanded()) {
									item.dispose();
								}
							} else {
								removedPath = getTreePathFromItem(item);
								disassociate(item);
								item.dispose();
