		Composite compositeWsPath = new Composite(grpWindowTitle, SWT.NONE);
		compositeWsPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_compositeWsPath = new GridLayout(2, false);
		gl_compositeWsPath.marginLeft = 12;
		gl_compositeWsPath.marginHeight = 0;
		compositeWsPath.setLayout(gl_compositeWsPath);

		Label locationLabel = new Label(compositeWsPath, SWT.NONE);
		locationLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
