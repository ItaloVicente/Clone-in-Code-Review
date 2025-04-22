		Composite res = (Composite) super.createDialogArea(parent);
		res.setLayout(new GridLayout(2, false));
		Label descLabel = new Label(res, SWT.NONE);
		descLabel.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, false, false, 2, 1));
		descLabel.setText(WorkbenchMessages.ContentTypes_newContentTypeDialog_descritption);
		Label nameLabel = new Label(res, SWT.NONE);
