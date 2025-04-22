		selectAllButton = createButton(buttonComposite, IDialogConstants.SELECT_ALL_ID, SELECT_ALL_TITLE, false);
		selectAllButton.addSelectionListener(widgetSelectedAdapter(e -> setAllChecked(true)));
		deselectAllButton = createButton(buttonComposite, IDialogConstants.DESELECT_ALL_ID, DESELECT_ALL_TITLE, false);
		deselectAllButton.addSelectionListener(widgetSelectedAdapter(e -> setAllChecked(false)));
		if (useShortButtonLabels) {
			selectAllButton.setText(WorkbenchMessages.SelectionDialog_selectLabel_short);
			deselectAllButton.setText(WorkbenchMessages.SelectionDialog_deselectLabel_short);
		}
	}
