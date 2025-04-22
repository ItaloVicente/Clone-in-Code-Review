	}

	private void internalCollectChecked(List result, Widget widget) {
		Item[] items = getChildren(widget);
		for (Item item : items) {
			if (item instanceof TreeItem && ((TreeItem) item).getChecked()) {
				Object data = item.getData();
				if (data != null) {
