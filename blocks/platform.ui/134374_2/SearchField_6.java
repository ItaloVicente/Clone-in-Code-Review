		Label expandResultsLabel = quickAccessContents.createInfoLabel(shell);
		Label showAsDialogLabel = new Label(shell, SWT.NONE);
		showAsDialogLabel.setText(NLS.bind(QuickAccessMessages.QuickAccess_ShowAsDialog, "F2"));
		showAsDialogLabel.setBackground(expandResultsLabel.getBackground());
		showAsDialogLabel.setForeground(expandResultsLabel.getForeground());
		showAsDialogLabel.setFont(expandResultsLabel.getFont());
		showAsDialogLabel.setLayoutData(GridDataFactory.copyData((GridData) expandResultsLabel.getLayoutData()));
