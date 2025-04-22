	private void createHandlerLocationControls(Composite parent) {
		handlerComposite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(handlerComposite);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(handlerComposite);

		new Label(handlerComposite, SWT.NONE).setText(IDEWorkbenchMessages.UrlHandlerPreferencePage_Handler_Label);

		handlerLocation = new Text(handlerComposite, SWT.READ_ONLY | SWT.BORDER);
		handlerLocation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		handlerComposite.setVisible(false); // set visible on table selection
