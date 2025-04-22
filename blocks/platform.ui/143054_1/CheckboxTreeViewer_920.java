		if (lastClickedItem != null) {
			TreeItem item = lastClickedItem;
			Object data = item.getData();
			if (data != null) {
				boolean state = item.getChecked();
				setChecked(data, !state);
				fireCheckStateChanged(new CheckStateChangedEvent(this, data,
						!state));
			}
			lastClickedItem = null;
		} else {
