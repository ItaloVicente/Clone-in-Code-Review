		if (expand && w instanceof Item) {
			Item item = getParentItem((Item) w);
			LinkedList<Item> toExpandList = new LinkedList<>();
			while (item != null) {
				if (!getExpanded(item)) {
					toExpandList.addFirst(item);
				}
				item = getParentItem(item);
			}
			for (Item toExpand : toExpandList) {
				setExpanded(toExpand, true);
			}
		}
