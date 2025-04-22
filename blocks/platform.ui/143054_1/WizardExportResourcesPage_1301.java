				resourceGroup.setAllSelections(true);
				updateWidgetEnablements();
			}
		};
		selectButton.addSelectionListener(listener);
		selectButton.setFont(font);
		setButtonLayoutData(selectButton);

		Button deselectButton = createButton(buttonComposite, IDialogConstants.DESELECT_ALL_ID, DESELECT_ALL_TITLE,
				false);

		listener = new SelectionAdapter() {
			@Override
