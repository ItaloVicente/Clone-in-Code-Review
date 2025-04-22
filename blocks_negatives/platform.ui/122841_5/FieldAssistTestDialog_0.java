		dec.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				MessageDialog
						.openInformation(
								getShell(),
								TaskAssistExampleMessages.ExampleDialog_SelectionTitle,
								TaskAssistExampleMessages.ExampleDialog_SelectionMessage);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
