					/**
					 * Return the number of filtered items
					 * @return int
					 */
					private int getFilteredItemsCount() {
						int total = 0;
						TreeItem[] items = getViewer().getTree().getItems();
						for (TreeItem item : items) {
							total += itemCount(item);
