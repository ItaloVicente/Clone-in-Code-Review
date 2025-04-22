		new Label(composite, SWT.NONE).setText(ActivityMessages.ActivityEnabler_description);

		descriptionText = new Text(composite, SWT.READ_ONLY | SWT.WRAP | SWT.BORDER
				| SWT.V_SCROLL);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.heightHint = Dialog.convertHeightInCharsToPixels(fontMetrics, 5);
		descriptionText.setLayoutData(data);
		setInitialStates();
