		dec.addSelectionListener(widgetDefaultSelectedAdapter(e-> {
			MessageDialog
			.openInformation(
					getShell(),
					TaskAssistExampleMessages.ExampleDialog_DefaultSelectionTitle,
					TaskAssistExampleMessages.ExampleDialog_DefaultSelectionMessage);
