		Object obj = itemModel.getParent();
		if (!(obj instanceof MToolBar)) {
			return;
		}
		ToolBarManager parent = getManager((MToolBar) obj);
		if (itemModel.isToBeRendered()) {
			if (parent != null) {
				modelProcessSwitch(parent, itemModel);
				updateWidget(parent);
