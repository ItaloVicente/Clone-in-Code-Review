		final Text filterText = new Text(outer, SWT.BORDER | SWT.SEARCH | SWT.ICON_CANCEL | SWT.ICON_SEARCH);
		filterText.setLayoutData(GridDataFactory.fillDefaults().create());
		filterText.setFont(JFaceResources.getDialogFont());
		filterText.setMessage(WorkbenchMessages.AboutPluginsDialog_filterTextMessage);
		filterText.setFocus();

