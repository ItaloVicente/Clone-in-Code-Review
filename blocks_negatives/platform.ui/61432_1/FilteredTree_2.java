					/**
					 * Return the count of treeItem and it's children to
					 * infinite depth.
					 *
					 * @param treeItem
					 * @return int
					 */
					private int itemCount(TreeItem treeItem) {
						int count = 1;
						TreeItem[] children = treeItem.getItems();
						for (TreeItem element : children) {
							count += itemCount(element);
