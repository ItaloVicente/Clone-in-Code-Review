		deselectAllButton.setToolTipText(WorkbenchMessages.SelectionDialog_deselectLabel);
		deselectAllButton.addSelectionListener(widgetSelectedAdapter(e -> setAllChecked(false)));
	}

	private void setAllChecked(boolean state) {
		listViewer.setAllChecked(state);
		updateButtonsOnSelection();
	}
