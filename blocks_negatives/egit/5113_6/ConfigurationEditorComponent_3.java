		Composite valuePanel = new Composite(main, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(valuePanel);
		valuePanel.setLayout(new GridLayout(3, false));
		new Label(valuePanel, SWT.NONE)
				.setText(UIText.ConfigurationEditorComponent_ValueLabel);
		valueText = new Text(valuePanel, SWT.BORDER);
		valueText
				.setText(UIText.ConfigurationEditorComponent_NoEntrySelectedMessage);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true,
				false).applyTo(valueText);
		Composite buttonContainer = new Composite(valuePanel, SWT.NONE);
		buttonContainer.setLayout(new GridLayout(3, true));
		changeValue = new Button(buttonContainer, SWT.PUSH);
		GridDataFactory.fillDefaults().applyTo(changeValue);
		changeValue.setText(UIText.ConfigurationEditorComponent_ChangeButton);
		changeValue.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection sel = (IStructuredSelection) tv
						.getSelection();
				Object first = sel.getFirstElement();
				if (first instanceof Entry) {
					Entry entry = (Entry) first;
					entry.changeValue(valueText.getText());
					markDirty();
				}
			}
		});
		deleteValue = new Button(buttonContainer, SWT.PUSH);
		GridDataFactory.fillDefaults().applyTo(deleteValue);
		deleteValue.setText(UIText.ConfigurationEditorComponent_DeleteButton);
		deleteValue.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection sel = (IStructuredSelection) tv
						.getSelection();
				Object first = sel.getFirstElement();
				if (first instanceof Entry) {
					Entry entry = (Entry) first;
					entry.removeValue();
					markDirty();
				}

			}
		});
		addValue = new Button(buttonContainer, SWT.PUSH);
		GridDataFactory.fillDefaults().applyTo(addValue);
		addValue.setText(UIText.ConfigurationEditorComponent_AddButton);
		addValue.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection sel = (IStructuredSelection) tv
						.getSelection();
				Object first = sel.getFirstElement();
				if (first instanceof Entry) {
					Entry entry = (Entry) first;
					entry.addValue(valueText.getText());
					markDirty();
				}

			}
		});

