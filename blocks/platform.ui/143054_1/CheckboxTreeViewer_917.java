	private void applyState(CustomHashtable checked, CustomHashtable grayed,
			Widget widget) {
		Item[] items = getChildren(widget);
		for (Item item : items) {
			if (item instanceof TreeItem) {
				Object data = item.getData();
				if (data != null) {
					TreeItem ti = (TreeItem) item;
					ti.setChecked(checked.containsKey(data));
					ti.setGrayed(grayed.containsKey(data));
				}
			}
			applyState(checked, grayed, item);
		}
	}

	protected void fireCheckStateChanged(final CheckStateChangedEvent event) {
