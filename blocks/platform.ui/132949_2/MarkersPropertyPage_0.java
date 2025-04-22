	private static Text createReadOnlyText(Composite parent) {
		Text text = new Text(parent, SWT.SINGLE | SWT.READ_ONLY);
		text.setBackground(text.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		text.setLayoutData(gridData);
		return text;
	}

