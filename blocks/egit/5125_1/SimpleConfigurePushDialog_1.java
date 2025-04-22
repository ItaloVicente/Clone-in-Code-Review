
		final Composite uriButtonArea = new Composite(pushUriDetails, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(uriButtonArea);
		GridDataFactory.fillDefaults().grab(false, true).applyTo(uriButtonArea);

		Button addUri = new Button(uriButtonArea, SWT.PUSH);
