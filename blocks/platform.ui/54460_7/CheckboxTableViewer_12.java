		if (event.detail == SWT.CHECK) {
			super.handleSelect(event); // this will change the current selection

			TableItem item = (TableItem) event.item;
			Object data = item.getData();
			if (data != null) {
				fireCheckStateChanged(new CheckStateChangedEvent(this, data,
						item.getChecked()));
			}
		} else {
