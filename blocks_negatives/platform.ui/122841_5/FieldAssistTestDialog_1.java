		dec.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
				MessageDialog
						.openInformation(
								getShell(),
								TaskAssistExampleMessages.ExampleDialog_DefaultSelectionTitle,
								TaskAssistExampleMessages.ExampleDialog_DefaultSelectionMessage);
			}
