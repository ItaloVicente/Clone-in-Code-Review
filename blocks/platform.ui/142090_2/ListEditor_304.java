		super.setEnabled(enabled, parent);
		getListControl(parent).setEnabled(enabled);
		addButton.setEnabled(enabled);
		removeButton.setEnabled(enabled);
		upButton.setEnabled(enabled);
		downButton.setEnabled(enabled);
	}

	protected Button getAddButton() {
		return addButton;
	}

	protected Button getRemoveButton() {
		return removeButton;
	}

	protected Button getUpButton() {
		return upButton;
	}

	protected Button getDownButton() {
		return downButton;
	}

	protected List getList() {
		return list;
	}
