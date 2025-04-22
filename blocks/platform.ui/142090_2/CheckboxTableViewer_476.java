		Widget widget = findItem(element);
		if (widget instanceof TableItem) {
			return ((TableItem) widget).getChecked();
		}
		return false;
	}

