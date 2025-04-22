	}

	private void internalCollectChecked(List result, Widget widget) {
		Item[] items = getChildren(widget);
		for (int i = 0; i < items.length; i++) {
			Item item = items[i];
			if (item instanceof TreeItem && ((TreeItem) item).getChecked()) {
				Object data = item.getData();
				if (data != null) {
