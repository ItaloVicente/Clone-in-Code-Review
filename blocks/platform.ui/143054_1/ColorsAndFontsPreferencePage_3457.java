	private Button createButton(Composite parent, String label) {
		Button button = new Button(parent, SWT.PUSH | SWT.CENTER);
		button.setText(label);
		myApplyDialogFont(button);
		setButtonLayoutData(button);
		button.setEnabled(false);
		return button;
	}
