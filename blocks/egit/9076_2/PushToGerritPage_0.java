
		Label reviewerLabel = new Label(main, SWT.NONE);
		reviewerLabel.setText("Reviewer:"); //$NON-NLS-1$

		reviewerText = new Text(main, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1)
		.applyTo(reviewerText);

