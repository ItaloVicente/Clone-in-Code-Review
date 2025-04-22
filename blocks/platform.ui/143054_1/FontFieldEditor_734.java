	}

	protected Button getChangeControl(Composite parent) {
		if (changeFontButton == null) {
			changeFontButton = new Button(parent, SWT.PUSH);
			if (changeButtonText != null) {
