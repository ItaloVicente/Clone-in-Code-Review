				handleTypesEditButtonPressed();
			}
		};
		selectTypesButton.addSelectionListener(listener);
		selectTypesButton.setFont(font);
		setButtonLayoutData(selectTypesButton);

		Button selectButton = createButton(buttonComposite, IDialogConstants.SELECT_ALL_ID, SELECT_ALL_TITLE, false);

		listener = new SelectionAdapter() {
			@Override
