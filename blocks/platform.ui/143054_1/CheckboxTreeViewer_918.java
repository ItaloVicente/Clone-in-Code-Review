					l.checkStateChanged(event);
				}
			});
		}

	}

	private void gatherState(CustomHashtable checked, CustomHashtable grayed,
			Widget widget) {
		Item[] items = getChildren(widget);
		for (Item item : items) {
			if (item instanceof TreeItem) {
				Object data = item.getData();
				if (data != null) {
					TreeItem ti = (TreeItem) item;
					if (ti.getChecked()) {
