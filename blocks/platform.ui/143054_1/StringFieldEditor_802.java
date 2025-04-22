		return getPreferenceStore().getString(getPreferenceName());
	}

	protected Text getTextControl() {
		return textField;
	}

	public Text getTextControl(Composite parent) {
		if (textField == null) {
			textField = new Text(parent, SWT.SINGLE | SWT.BORDER);
			textField.setFont(parent.getFont());
			switch (validateStrategy) {
			case VALIDATE_ON_KEY_STROKE:
				textField.addKeyListener(new KeyAdapter() {

					@Override
