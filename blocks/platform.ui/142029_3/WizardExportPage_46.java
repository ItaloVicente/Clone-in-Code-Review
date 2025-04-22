				return super.getShellStyle() | SWT.SHEET;
			}
		};

		dialog.setTitle(IDEWorkbenchMessages.WizardExportPage_resourceTypeDialog);
		dialog.open();

		return dialog.getResult();
	}

	protected void resetSelectedResources() {
		resourceDetailsDescription.setText(IDEWorkbenchMessages.WizardExportPage_detailsMessage);
		selectedResources = null;

		if (exportCurrentSelection) {
			exportCurrentSelection = false;

			if (resourceNameField.getText().length() > CURRENT_SELECTION.length()) {
				resourceNameField.setText(resourceNameField.getText().substring(CURRENT_SELECTION.length()));
