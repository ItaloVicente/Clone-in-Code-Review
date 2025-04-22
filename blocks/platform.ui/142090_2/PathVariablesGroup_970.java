				addNewVariable();
			}
		});
		addButton.setFont(font);
		setButtonLayoutData(addButton);

		editButton = new Button(groupComponent, SWT.PUSH);
		editButton.setText(IDEWorkbenchMessages.PathVariablesBlock_editVariableButton);
		editButton.addSelectionListener(new SelectionAdapter() {
			@Override
