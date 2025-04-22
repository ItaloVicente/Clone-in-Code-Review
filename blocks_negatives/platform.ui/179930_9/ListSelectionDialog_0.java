
		Button selectButton = createButton(buttonComposite, IDialogConstants.SELECT_ALL_ID, SELECT_ALL_TITLE, false);

		SelectionListener listener = widgetSelectedAdapter(e -> listViewer.setAllChecked(true));
		selectButton.addSelectionListener(listener);

		Button deselectButton = createButton(buttonComposite, IDialogConstants.DESELECT_ALL_ID, DESELECT_ALL_TITLE,
