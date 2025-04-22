					selectFolder();
				}
			});
			setButtonLayoutData(folderButton);
		}

		if (currentResource != null) {
			layout.numColumns++;
			variableButton = new Button(buttonsComposite, SWT.PUSH);
			variableButton.setText(IDEWorkbenchMessages.PathVariableDialog_variable);

			variableButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false));

			variableButton.addSelectionListener(new SelectionAdapter() {
				@Override
