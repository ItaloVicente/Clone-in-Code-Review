	private static void createButton(Composite parent, Color bgColor,
			boolean checked, boolean enabled) {
		Button button = new Button(parent, SWT.CHECK);
		button.setSelection(checked);
		button.setEnabled(enabled);
		button.setBackground(bgColor);
		Point bSize = button.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		button.setSize(bSize);
	}
