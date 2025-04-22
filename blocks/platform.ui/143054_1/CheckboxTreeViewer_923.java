			}
			internalCollectChecked(result, item);
		}
	}

	private void internalCollectGrayed(List result, Widget widget) {
		Item[] items = getChildren(widget);
		for (Item item : items) {
			if (item instanceof TreeItem && ((TreeItem) item).getGrayed()) {
				Object data = item.getData();
				if (data != null) {
