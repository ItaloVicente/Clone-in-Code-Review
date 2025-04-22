				return;
			}
			Widget[] items = internalFindItems(elementOrTreePath);
			for (Widget widget : items) {
				TreeItem item = (TreeItem) widget;
				if (!hasChildren) {
					item.setItemCount(0);
				} else {
					if (!item.getExpanded()) {
						item.setItemCount(1);
						TreeItem child = item.getItem(0);
						if (child.getData() != null) {
							disassociate(child);
						}
						item.clear(0, true);
