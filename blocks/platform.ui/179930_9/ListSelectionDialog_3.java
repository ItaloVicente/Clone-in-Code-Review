		Button selectAllButton = createButton(buttonComposite, IDialogConstants.SELECT_ALL_ID,
				WorkbenchMessages.SelectionDialog_selectLabel, false);
		selectAllButton.setToolTipText(WorkbenchMessages.SelectionDialog_selectLabel);
		selectAllButton.addSelectionListener(widgetSelectedAdapter(e -> setAllChecked(true)));
		Button deselectAllButton = createButton(buttonComposite, IDialogConstants.DESELECT_ALL_ID,
				WorkbenchMessages.SelectionDialog_deselectLabel,
