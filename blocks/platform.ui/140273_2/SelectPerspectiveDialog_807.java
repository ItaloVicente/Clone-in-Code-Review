	}

	protected void updateButtons() {
		okButton.setEnabled(getSelection() != null);
	}

	protected void updateSelection(SelectionChangedEvent event) {
		perspDesc = null;
