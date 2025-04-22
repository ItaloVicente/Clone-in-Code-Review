		if (expand && w instanceof Item) {
			Item item = getParentItem((Item) w);
			LinkedList toExpandList = new LinkedList();
			while (item != null) {
				if (!getExpanded(item)) {
					toExpandList.addFirst(item);
				}
				item = getParentItem(item);
			}
			for (Iterator it = toExpandList.iterator(); it.hasNext();) {
				Item toExpand = (Item) it.next();
				setExpanded(toExpand, true);
			}
		}
