			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection sel = (IStructuredSelection) tv
						.getSelection();
				Object first = sel.getFirstElement();
				if (first instanceof Entry) {
					Entry entry = (Entry) first;
					entry.changeValue(valueText.getText());
					saveAndUpdate();
				}
			}
		});
		removeValue = new Button(valuePanel, SWT.PUSH);
		removeValue
				.setText(UIText.GlobalConfigurationPreferencePage_RemoveButton);
		removeValue.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection sel = (IStructuredSelection) tv
						.getSelection();
				Object first = sel.getFirstElement();
				if (first instanceof Entry) {
					Entry entry = (Entry) first;
					entry.removeValue();
					saveAndUpdate();
				}

