	protected void checkStateChanged(CheckStateChangedEvent event) {
		if (event.getChecked())
			checkedItems.add(event.getElement());
		else
			checkedItems.remove(event.getElement());
	}
