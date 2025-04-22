		ToolItem[] mi = toolBar.getItems();
		ArrayList<ToolItem> toRemove = new ArrayList<ToolItem>(mi.length);
		for (ToolItem item : mi) {
			if (item == null) {
				continue;
			}
