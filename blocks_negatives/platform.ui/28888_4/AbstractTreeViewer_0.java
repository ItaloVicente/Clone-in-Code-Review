					if (expand && pw instanceof Item) {
						Item item = (Item) pw;
						LinkedList<Item> toExpandList = new LinkedList<>();
						while (item != null && !getExpanded(item)) {
							toExpandList.addFirst(item);
							item = getParentItem(item);
						}
						for (Item toExpand : toExpandList) {
							setExpanded(toExpand, true);
						}
					}
