	private Label createLabel(Composite composite, String text) {
		Label label = new Label(composite, SWT.NONE);
		GridData data = new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1);
		label.setLayoutData(data);
		label.setText(text);
		return label;
	}

