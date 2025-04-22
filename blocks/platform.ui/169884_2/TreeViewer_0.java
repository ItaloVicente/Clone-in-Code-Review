	@Override
	public Item[] getChildrenForUpdate(Object parent, Widget widget,
			Object[] elementChildren) {
		Item[] items = super.getChildrenForUpdate(parent, widget, elementChildren);

		if (!(parent == null || parent.equals(getRoot())))
			return items;
		if (elementChildren == null || elementChildren.length == 0 || items.length / elementChildren.length > 5) {
			removeAll(getControl());
			unmapAllElements();
			items = getChildren(widget);
		}
		return items;
	}


