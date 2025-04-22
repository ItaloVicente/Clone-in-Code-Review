	private void createFileAssociations(final Composite composite) {
		{
			Label label = new Label(composite, SWT.NONE);
			label.setText(WorkbenchMessages.ContentTypes_fileAssociationsLabel);
			GridData data = new GridData();
			data.horizontalSpan = 2;
			label.setLayoutData(data);
		}
