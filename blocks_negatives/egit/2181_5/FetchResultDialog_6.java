		final Label label = new Label(composite, SWT.NONE);
		final String text;
		if (!result.getTrackingRefUpdates().isEmpty())
			text = NLS.bind(UIText.FetchResultDialog_labelNonEmptyResult,
					sourceString);
		else
			text = NLS.bind(UIText.FetchResultDialog_labelEmptyResult,
					sourceString);
		label.setText(text);
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
